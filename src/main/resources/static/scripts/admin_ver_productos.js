
async function cargarProductos() {
    const res = await fetch("http://localhost:8080/api/productos");
    return await res.json();
}

    async function renderProductos() {
    const productos = await cargarProductos();
    const contenedor = document.getElementById("productos-container");
    contenedor.innerHTML = "";

    productos.forEach(producto => {
    const item = document.createElement("div");
    item.classList.add("accordion-item");

    const header = document.createElement("div");
    header.classList.add("accordion-header");
    header.textContent = producto.nombreProducto;

    const body = document.createElement("div");
    body.classList.add("accordion-body");
    body.innerHTML = `
                    <p><strong>Descripción:</strong> ${producto.descripcion}</p>
                    <p><strong>Precio Unitario:</strong> $${producto.precioUnitario}</p>
                    <p><strong>Cantidad Disponible:</strong> ${producto.cantidadDisponible}</p>
                `;

    // Evento toggle
    header.addEventListener("click", () => {
    body.style.display = (body.style.display === "none") ? "block" : "none";
});

    body.style.display = "none";

    item.appendChild(header);
    item.appendChild(body);
    contenedor.appendChild(item);
});
}

renderProductos();