document.addEventListener("DOMContentLoaded", () => {
    const clienteSelect = document.getElementById("cliente");
    const empleadoSelect = document.getElementById("empleado");
    const detallesContainer = document.getElementById("detallesContainer");
    const totalVentaSpan = document.getElementById("totalVenta");
    let productos = [];

    // Obtener productos disponibles
    fetch("/api/productos")
        .then(res => res.json())
        .then(data => productos = data);

    // Cargar clientes
    fetch("/api/clientes")
        .then(res => res.json())
        .then(clientes => {
            clientes.forEach(c => {
                const op = document.createElement("option");
                op.value = c.idCliente;
                op.textContent = `${c.nombreCliente} ${c.aPaterno || ""} ${c.aMaterno || ""}`;
                clienteSelect.appendChild(op);
            });
        });

    // Cargar empleados
    fetch("/api/empleados")
        .then(res => res.json())
        .then(empleados => {
            empleados.forEach(e => {
                const op = document.createElement("option");
                op.value = e.idEmpleado;
                op.textContent = `${e.nombre} ${e.aPaterno || ""}`;
                empleadoSelect.appendChild(op);
            });
        });

    window.agregarDetalle = function () {
        const index = document.querySelectorAll(".detalle-item").length;
        const div = document.createElement("div");
        div.classList.add("detalle-item");

        div.innerHTML = `
      <label>Producto:</label>
      <select name="producto" required>
        ${productos.map(p => `<option value="${p.idProducto}" data-precio="${p.precioUnitario || 0}">${p.nombreProducto}</option>`).join('')}
      </select>
      <label>Cantidad:</label>
      <input type="number" name="cantidad" min="1" value="1" required>
      <span class="subtotal">$0.00</span>
      <button type="button" onclick="this.parentElement.remove(); calcularTotal()">Eliminar</button>
      <hr>
    `;

        div.querySelector('select[name="producto"]').addEventListener("change", calcularSubtotal);
        div.querySelector('input[name="cantidad"]').addEventListener("input", calcularSubtotal);
        detallesContainer.appendChild(div);

        calcularSubtotal.call(div.querySelector('select[name="producto"]'));
        calcularTotal();
    };

    function calcularSubtotal() {
        const contenedor = this.closest(".detalle-item");
        const productoSel = contenedor.querySelector("select[name='producto']");
        const cantidad = parseInt(contenedor.querySelector("input[name='cantidad']").value) || 1;
        const precio = parseFloat(productoSel.selectedOptions[0].dataset.precio);
        const subtotal = cantidad * precio;
        contenedor.querySelector(".subtotal").textContent = `$${subtotal.toFixed(2)}`;
        calcularTotal();
    }

    function calcularTotal() {
        let total = 0;
        document.querySelectorAll(".detalle-item").forEach(item => {
            const precio = parseFloat(item.querySelector("select[name='producto']").selectedOptions[0].dataset.precio);
            const cantidad = parseInt(item.querySelector("input[name='cantidad']").value) || 1;
            total += precio * cantidad;
        });
        totalVentaSpan.textContent = total.toFixed(2);
    }

    // Enviar formulario
    document.getElementById("formVenta").addEventListener("submit", async (e) => {
        e.preventDefault();

        const venta = {
            cliente: { idCliente: parseInt(clienteSelect.value) },
            empleado: { idEmpleado: parseInt(empleadoSelect.value) },
            metodoPago: document.getElementById("metodoPago").value,
            fechaVenta: document.getElementById("fechaVenta").value,
            costoVenta: parseFloat(totalVentaSpan.textContent)
        };

        const ventaRes = await fetch("/api/ventas", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(venta)
        });

        if (!ventaRes.ok) return alert("Error al registrar venta");

        const ventaGuardada = await ventaRes.json();

        const detalles = Array.from(document.querySelectorAll(".detalle-item")).map(item => {
            return {
                venta: { idVenta: ventaGuardada.idVenta },
                producto: { idProducto: parseInt(item.querySelector("select").value) },
                cantidad: parseInt(item.querySelector("input").value),
                precioUnitario: parseFloat(item.querySelector("select").selectedOptions[0].dataset.precio)
            };
        });

        for (let d of detalles) {
            await fetch("/api/detalles-venta", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(d)
            });
        }

        alert("Venta registrada con éxito");
        window.location.href = "empleado_home.html"; //cuando se realice la venta lo mandara a empleados home
    });
});
