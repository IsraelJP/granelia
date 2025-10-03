console.log("El archivo r_products.js está cargado");

const API_BASE = "http://localhost:8080/granelia/resources"; 

document.getElementById("formProducto").addEventListener("submit" , crearProduct);

async function crearProduct(e) {
     e.preventDefault();

    const form = e.currentTarget || e.target.closest('form');
    if (!form) {
    console.warn("No se encontró el formulario");
    return;
  }

    const fd = new FormData(form);

     const params = new URLSearchParams({
        idProduct: fd.get("idProduct") ,
        nameProduct: fd.get("nameProduct") ,
        price: fd.get("price") ,
        typeProduct: fd.get("typeProduct") ,
        expDate: fd.get("expDate") 
    });
    
    const url = `${API_BASE}/products?${params.toString()}`;
    const r = await fetch(url, { headers: { "Accept": "application/json" } });
    const data = await r.json();
    console.log("Respuesta:", data);
}

