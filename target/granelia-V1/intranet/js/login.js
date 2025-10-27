console.log("JS de Login Cargado");

const API_BASE = "https://israeljp-profile.com/granelia/resources";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formLogin");
  if (!form) {
    console.warn("No se encontró el formulario #formLogin");
    return;
  }
  form.addEventListener("submit", buscarUsu);
});

async function buscarUsu(e) {
  e.preventDefault();

  const form = e.currentTarget || e.target.closest("form");
  const btn = form.querySelector("[type=submit]");
  const msg = document.getElementById("msg"); // opcional, para mensajes en UI

  // Helpers UI
  const setMsg = (text, type = "info") => {
    if (!msg) return;
    msg.textContent = text;
    msg.className = "";            // limpia clases previas
    msg.classList.add(type);       // ej. .info .ok .warn .err (defínelas en CSS)
  };

  if (!form) {
    console.warn("No se encontró el formulario");
    return;
  }

  // Obtiene datos
  const fd = new FormData(form);
  const username = (fd.get("username") || "").trim();
  const password = (fd.get("password") || "").trim();

  // Validación mínima
  if (!username || !password) {
    setMsg("Por favor ingresa usuario y contraseña.", "warn");
    return;
  }

  // Deshabilita submit mientras se procesa
  if (btn) btn.disabled = true;
  setMsg("Autenticando…", "info");

  const url = `${API_BASE}/users/login`;

  try {
    const r = await fetch(url, {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ username, password })
    });

    // Manejo de estados HTTP comunes
    if (r.status === 400) {
      setMsg("Solicitud inválida (faltan datos o formato incorrecto).", "warn");
      return;
    }
    if (r.status === 401) {
      setMsg("Credenciales inválidas.", "err");
      return;
    }
    if (!r.ok) {
      // 500, 404 u otros
      setMsg(`Error del servidor (${r.status}). Intenta más tarde.`, "err");
      return;
    }

    // Intenta leer JSON
    let data = null;
    try {
      data = await r.json();
    } catch {
      // Si el backend devuelve vacío o texto
      data = null;
    }

    console.log("Respuesta:", data);

    // Soporta dos formatos:
    // 1) { ok: true, message: "..." }
    // 2) true / false
    const ok =
      (data && typeof data === "object" && "ok" in data && !!data.ok) ||
      (typeof data === "boolean" && data === true);

    if (!ok) {
      const message =
        (data && typeof data === "object" && data.message) ?
        data.message : "Credenciales inválidas.";
      setMsg(message, "err");
      return;
    }

    // Éxito
    setMsg("Autenticado. Redirigiendo…", "ok");

    // TODO: ajusta la ruta de destino
    // window.location.href = "/granelia/panel.html";
  } catch (err) {
    console.error("Error en fetch:", err);
    setMsg("No se pudo conectar con el servidor. Revisa tu red o CORS.", "err");
  } finally {
    if (btn) btn.disabled = false;
  }
}

console.log("JS de Login Cargado");

const API_BASE = "https://israeljp-profile.com/granelia/resources";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formLogin");
  if (!form) {
    console.warn("No se encontró el formulario #formLogin");
    return;
  }
  form.addEventListener("submit", buscarUsu);
});

async function buscarUsu(e) {
  e.preventDefault();
  const form = e.currentTarget || e.target.closest("form");
  const btn = form.querySelector("[type=submit]");
  const msg = document.getElementById("msg");

  // Helpers UI
  const setMsg = (text, type = "info") => {
    if (!msg) return;
    msg.textContent = text;
    msg.style.display = "block";
    msg.className = "";            // limpia clases previas
    msg.classList.add(type);       // ej. .info .ok .warn .err
  };


  if (!form) {
    console.warn("No se encontró el formulario");
    return;
  }

  // Obtiene datos
  const fd = new FormData(form);
  const username = (fd.get("username") || "").trim();
  const password = (fd.get("password") || "").trim();

  // Validación mínima
  if (!username || !password) {
    setMsg("Por favor ingresa usuario y contraseña.", "warn");
    return;
  }

  // Deshabilita submit mientras se procesa
  if (btn) btn.disabled = true;
  setMsg("Autenticando…", "info");

  const url = `${API_BASE}/users/login`;

  try {
    const r = await fetch(url, {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ username, password })
    });

    // Manejo de estados HTTP comunes
    if (r.status === 400) {
      setMsg("Solicitud inválida (faltan datos o formato incorrecto).", "warn");
      return;
    }
    if (r.status === 401) {
      setMsg("Credenciales inválidas.", "err");
      return;
    }
    if (!r.ok) {
      // 500, 404 u otros
      setMsg(`Error del servidor (${r.status}). Intenta más tarde.`, "err");
      return;
    }

    // Intenta leer JSON
    let data = null;
    try {
      data = await r.json();
    } catch {
      // Si el backend devuelve vacío o texto
      data = null;
    }

    console.log("Respuesta:", data);

    // Soporta dos formatos:
    // 1) { ok: true, message: "..." }
    // 2) true / false
    const ok =
      (data && typeof data === "object" && "ok" in data && !!data.ok) ||
      (typeof data === "boolean" && data === true);

    if (!ok) {
      const message =
        (data && typeof data === "object" && data.message) ?
        data.message : "Credenciales inválidas.";
      setMsg(message, "err");
      return;
    }

      // Éxito
    setMsg("Autenticado. Redirigiendo…", "ok");
    // TODO: ajusta la ruta de destino
    // window.location.href = "/granelia/panel.html";

  } catch (err) {
    console.error("Error en fetch:", err);
    setMsg("No se pudo conectar con el servidor. Revisa tu red o CORS.", "err");
  } finally {
    if (btn) btn.disabled = false;
  }
}