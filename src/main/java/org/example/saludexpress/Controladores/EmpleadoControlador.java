package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Empleado;
import org.example.saludexpress.Repositorios.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    // Obtener todos los empleados
    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoRepositorio.findAll();
    }

    // Obtener empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Integer id) {
        Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(id);
        return empleadoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo empleado
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        Empleado nuevoEmpleado = empleadoRepositorio.save(empleado);
        return ResponseEntity.ok(nuevoEmpleado);
    }

    // Actualizar un empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoDetalles) {
        Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(id);
        if (!empleadoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = empleadoOpt.get();
        empleado.setNombre(empleadoDetalles.getNombre());
        empleado.setaPaterno(empleadoDetalles.getaPaterno());
        empleado.setaMaterno(empleadoDetalles.getaMaterno());
        empleado.setFechaNacimiento(empleadoDetalles.getFechaNacimiento());
        empleado.setFechaContratacion(empleadoDetalles.getFechaContratacion());
        empleado.setCalle(empleadoDetalles.getCalle());
        empleado.setColonia(empleadoDetalles.getColonia());
        empleado.setCodigoPostal(empleadoDetalles.getCodigoPostal());
        empleado.setTelefono(empleadoDetalles.getTelefono());
        empleado.setCorreo(empleadoDetalles.getCorreo());
        empleado.setSucursal(empleadoDetalles.getSucursal());
        empleado.setPuesto(empleadoDetalles.getPuesto());
        empleado.setEstado(empleadoDetalles.getEstado());
        empleado.setMunicipio(empleadoDetalles.getMunicipio());

        Empleado empleadoActualizado = empleadoRepositorio.save(empleado);
        return ResponseEntity.ok(empleadoActualizado);
    }

    // Eliminar empleado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        if (!empleadoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empleadoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
