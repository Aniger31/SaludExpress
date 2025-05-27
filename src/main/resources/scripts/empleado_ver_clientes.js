async function cargarClientes() {
    const res = await fetch("http://localhost:8080/api/clientes");
    return await res.json();
}

async function renderClientes() {
    const clientes = await cargarClientes();
    const contenedor = document.getElementById("clientes-container");
    contenedor.innerHTML = "";

    clientes.forEach(cliente => {
        const item = document.createElement("div");
        item.classList.add("accordion-item");

        const header = document.createElement("div");
        header.classList.add("accordion-header");
        header.textContent = `${cliente.nombreCliente} ${cliente.aPaterno} ${cliente.aMaterno}`;

        const body = document.createElement("div");
        body.classList.add("accordion-body");
        body.innerHTML = `
                    <p><strong>Correo:</strong> ${cliente.correo}</p>
                    <p><strong>Teléfono:</strong> ${cliente.telefono}</p>
                    <p><strong>ID Cliente:</strong> ${cliente.idCliente}</p>
                `;

        // Toggle
        header.addEventListener("click", () => {
            body.style.display = (body.style.display === "none") ? "block" : "none";
        });

        item.appendChild(header);
        item.appendChild(body);
        contenedor.appendChild(item);
    });
}

renderClientes();