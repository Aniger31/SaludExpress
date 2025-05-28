// Función mejorada para cargar opciones
async function cargarOpciones(url, selectId, nombreCampo, idCampo) {
    try {
        const select = document.getElementById(selectId);
        select.disabled = true;
        select.innerHTML = '<option value="">Cargando...</option>';

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const data = await response.json();

        select.innerHTML = '';

        // Opción por defecto
        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = selectId === "municipio"
            ? "Seleccione un estado primero"
            : "Seleccione una opción";
        defaultOption.selected = true;
        defaultOption.disabled = true;
        select.appendChild(defaultOption);

        if (Array.isArray(data)) {
            data.forEach(item => {
                const option = document.createElement("option");
                option.value = item[idCampo];
                option.textContent = item[nombreCampo];
                select.appendChild(option);
            });
            select.disabled = false;
        } else {
            console.error(`Datos recibidos no son un array para ${selectId}:`, data);
            select.innerHTML = '<option value="">Error cargando opciones</option>';
        }
    } catch (error) {
        console.error(`Error cargando ${selectId} desde ${url}:`, error);
        const select = document.getElementById(selectId);
        select.innerHTML = '<option value="">Error cargando opciones</option>';
    }
}

// Cargar opciones al iniciar
document.addEventListener("DOMContentLoaded", async () => {
    try {
        // Cargar puestos
        await cargarOpciones("/api/puestos", "puesto", "nombrePuesto", "idPuesto");

        // Cargar estados
        await cargarOpciones("/estados", "estado", "nombreEstado", "idEstado");

        // Cargar sucursales
        await cargarOpciones("/sucursales", "sucursal", "colonia", "idSucursal");

        // Evento para cambio de estado
        document.getElementById("estado").addEventListener("change", async function() {
            const estadoId = this.value;
            const municipioSelect = document.getElementById("municipio");

            if (estadoId) {
                municipioSelect.disabled = false;
                await cargarOpciones(
                    `/api/municipios/estado/${estadoId}`,
                    "municipio",
                    "nombreMunicipio",
                    "idMunicipio"
                );
            } else {
                municipioSelect.innerHTML = '<option value="">Seleccione un estado primero</option>';
                municipioSelect.disabled = true;
            }
        });

        // Manejo del formulario - CORREGIDO
        document.getElementById("registroForm").addEventListener("submit", async function(e) {
            e.preventDefault();

            // Validar que todos los campos requeridos estén llenos
            const requiredFields = [
                'name', 'apellidoP', 'apellidoM', 'fechaN', 'fechaC',
                'email', 'telefono', 'calle', 'colonia', 'codigoP',
                'puesto', 'estado', 'municipio', 'sucursal'
            ];

            for (let fieldId of requiredFields) {
                const field = document.getElementById(fieldId);
                if (!field.value.trim()) {
                    alert(`Por favor complete el campo: ${field.previousElementSibling.textContent}`);
                    field.focus();
                    return;
                }
            }

            // Crear objeto con los nombres correctos que espera el backend
            const formData = {
                nombre: document.getElementById("name").value.trim(),
                aPaterno: document.getElementById("apellidoP").value.trim(),
                aMaterno: document.getElementById("apellidoM").value.trim(),
                fechaNacimiento: document.getElementById("fechaN").value,
                fechaContratacion: document.getElementById("fechaC").value,
                correo: document.getElementById("email").value.trim(),
                telefono: document.getElementById("telefono").value.trim(),
                calle: document.getElementById("calle").value.trim(),
                colonia: document.getElementById("colonia").value.trim(),
                codigoPostal: document.getElementById("codigoP").value.trim(),
                // Para las relaciones, necesitas enviar objetos con el ID
                puesto: {
                    idPuesto: parseInt(document.getElementById("puesto").value)
                },
                estado: {
                    idEstado: parseInt(document.getElementById("estado").value)
                },
                municipio: {
                    idMunicipio: parseInt(document.getElementById("municipio").value)
                },
                sucursal: {
                    idSucursal: parseInt(document.getElementById("sucursal").value)
                }
            };

            console.log("Datos a enviar:", formData);

            try {
                const response = await fetch("/api/empleados", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                console.log("Response status:", response.status);

                if (!response.ok) {
                    const errorText = await response.text();
                    console.error("Error response:", errorText);
                    throw new Error(`Error HTTP: ${response.status} - ${errorText}`);
                }

                const result = await response.json();
                console.log("Empleado creado:", result);
                alert("Empleado registrado exitosamente");

                // Limpiar el formulario
                document.getElementById("registroForm").reset();

                // Opcional: redirigir después de un breve delay
                setTimeout(() => {
                    window.location.href = "/admin_home.html";
                }, 1000);

            } catch (error) {
                console.error("Error al enviar el formulario:", error);
                alert("Error al registrar el empleado: " + error.message);
            }
        });
    } catch (error) {
        console.error("Error al cargar la página:", error);
    }
});