console.log("El archivo app.js está cargado");

const API_BASE = "https://israeljp-profile.com/granelia/"; 

document.getElementById("formUsuario")
  .addEventListener("submit", crearUsuarioPorQuery);

async function crearUsuarioPorQuery(e) {
  e.preventDefault();

  const form = e.currentTarget || e.target.closest('form');
  if (!form) {
    console.warn("No se encontró el formulario");
    return;
  }

  const fd = new FormData(form); 

  const params = new URLSearchParams({
    username: fd.get("username") || "",
    email: fd.get("email") || "",
    phone: fd.get("phone") || "",
    nameCompany: fd.get("nameCompany") || "",
    contactName: fd.get("contactName") || ""
  });

   const url = `${API_BASE}/users?${params.toString()}`;
  const r = await fetch(url, { headers: { "Accept": "application/json" } });
  const data = await r.json();
  console.log("Respuesta:", data);
}
