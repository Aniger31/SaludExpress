function cargarOpciones(url, selectId, nombreCampo, idCampo) {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById(selectId);
            data.forEach(item => {
                const option = document.createElement("option");
                option.value = item[idCampo];
                option.text = item[nombreCampo];
                select.appendChild(option);
            });
        })
        .catch(error => console.error(`Error cargando ${selectId}:`, error));
}

// Cargar opciones al cargar la página
document.addEventListener("DOMContentLoaded", () => {
    cargarOpciones("/api/puestos", "puesto", "nombrePuesto", "idPuesto");
    cargarOpciones("/api/estados", "estado", "nombreEstado", "idEstado");
    cargarOpciones("/api/municipios", "municipio", "nombreMunicipio", "idMunicipio");
    cargarOpciones("/api/sucursales", "sucursal", "colonia", "idSucursal");
});
