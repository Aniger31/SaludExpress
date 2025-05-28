document.addEventListener("DOMContentLoaded", () => {
    const productoSelect = document.getElementById("producto");
    const proveedorInput = document.getElementById("proveedor");
    const sucursalSelect = document.getElementById("sucursal");

    // Cargar productos
    fetch("/api/productos")
        .then(res => res.json())
        .then(productos => {
            productos.forEach(p => {
                const option = document.createElement("option");
                option.value = p.idProducto;
                option.textContent = p.nombreProducto;
                if (p.proveedor) {
                    option.dataset.proveedor = `${p.proveedor.nombreProveedor} ${p.proveedor.apaterno || ""} ${p.proveedor.amaterno || ""}`.trim();
                } else {
                    option.dataset.proveedor = "Proveedor no disponible";
                    option.dataset.proveedorId = "";
                }
                productoSelect.appendChild(option);
            });

            productoSelect.dispatchEvent(new Event('change'));
        });

    // Cargar sucursales
    fetch("/sucursales")
        .then(res => res.json())
        .then(sucursales => {
            sucursales.forEach(s => {
                const option = document.createElement("option");
                option.value = s.idSucursal;
                option.textContent = `${s.calle} #${s.numero}, ${s.colonia}`;
                sucursalSelect.appendChild(option);
            });
        });

    productoSelect.addEventListener("change", () => {
        const selected = productoSelect.options[productoSelect.selectedIndex];
        proveedorInput.value = selected.dataset.proveedor;
    });

    // Enviar formulario
    document.getElementById("formCompra").addEventListener("submit", (e) => {
        e.preventDefault();

        const selectedProduct = productoSelect.options[productoSelect.selectedIndex];

        const compra = {
            proveedor: { idProveedor: parseInt(selectedProduct.dataset.proveedorId) },
            sucursal: { idSucursal: parseInt(sucursalSelect.value) },
            fechaCompra: document.getElementById("fechaCompra").value
        };

        console.log("Iniciando registro de compra...");

        fetch("/api/compras-proveedor", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(compra)
        })
            .then(res => res.json())
            .then(compraRegistrada => {
                // Ahora registramos el detalle
                const detalle = {
                    compra: { idCompra: compraRegistrada.idCompra },
                    producto: { idProducto: parseInt(productoSelect.value) },
                    cantidad: parseInt(document.getElementById("cantidad").value),
                    precioUnitario: parseFloat(document.getElementById("precioUnitario").value)
                };

                console.log("Detalle que se enviará al backend:", detalle); // ← AGREGA ESTO


                return fetch("/api/detalles-compra", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(detalle)
                });
            })
            .then(res => {
                if (res.ok) {
                    alert("Compra y detalle registrados con éxito");
                    window.location.href = "admin_home.html"
                } else {
                    res.text().then(text => {
                        console.error("Error al registrar detalle:", res.status, text);
                        alert(`Error al registrar el detalle de compra: ${res.status} ${text}`);
                    });
                }
            })
            .catch(err => {
                console.error(err);
                alert("Error al registrar la compra");
            });
    });
});
