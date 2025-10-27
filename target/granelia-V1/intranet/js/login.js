console.log("[login] script cargado");

const API_BASE = window.API_BASE ?? "https://israeljp-profile.com/granelia/resources";

(function attachHandler() {
  const attach = () => {
    const form = document.querySelector("#formLogin");
    if (!form) {
      console.warn("[login] No se encontró #formLogin");
      return;
    }
    form.removeEventListener("submit", buscarUsu);
    form.addEventListener("submit", buscarUsu);
    console.log("[login] Handler de submit adjuntado a #formLogin");
  };

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", attach, { once: true });
  } else {
    attach();
  }
})();

async function buscarUsu(e) {
  e.preventDefault();
  console.log("[login] submit disparado");

  const form = e.currentTarget || e.target.closest("form");
  const btn  = form?.querySelector("[type=submit]");
  const msg  = document.getElementById("msg");

  const setMsg = (text, type = "info") => {
    if (!msg) {
      console.log(`[login][${type}]`, text);
      return;
    }
    msg.textContent = text;
    msg.className = "";
    msg.classList.add(type);
  };

  const fd = new FormData(form);
  const username = (fd.get("username") || "").trim();
  const password = (fd.get("password") || "").trim();
  console.log("[login] form data:", { username_len: username.length, password_len: password.length });

  if (!username || !password) {
    setMsg("Por favor ingresa usuario y contraseña.", "warn");
    return;
  }

  if (btn) btn.disabled = true;
  setMsg("Autenticando…", "info");

  // Construir URL con parámetros
  const url = `${API_BASE}/users/login?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;

  const opts = {
    method: "GET",
    headers: {
      "Accept": "application/json"
    },
    cache: "no-store"
  };

  console.log("[login] fetch →", url, opts);

  try {
    const r = await fetch(url, opts);
    console.log("[login] status:", r.status);

    if (!r.ok) {
      setMsg(`Error del servidor (${r.status}).`, "err");
      return;
    }

    let data;
    try {
      data = await r.json(); // esperamos 1 o 0
    } catch (eJson) {
      console.warn("[login] r.json() falló, intentando texto:", eJson);
      const txt = await r.text();
      console.log("[login] texto:", txt);
      data = txt;
    }

    console.log("[login] respuesta:", data);

    if (parseInt(data) === 1) {
      setMsg("✅ Accediendo…", "ok");
      
    } else {
      setMsg("❌ El usuario no existe o la contraseña es incorrecta.", "err");
    }

  } catch (err) {
    console.error("[login] Error en fetch:", err);
    setMsg("No se pudo conectar con el servidor. Revisa tu red o CORS.", "err");
  } finally {
    if (btn) btn.disabled = false;
  }
}
