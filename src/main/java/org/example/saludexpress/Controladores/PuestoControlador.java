package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Puesto;
import org.example.saludexpress.Repositorios.PuestoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puestos")
public class PuestoControlador {

    @Autowired
    private PuestoRepositorio puestoRepositorio;

    // Obtener todos los puestos
    @GetMapping
    public List<Puesto> listarPuestos() {
        return puestoRepositorio.findAll();
    }

    // Obtener puesto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Puesto> obtenerPuestoPorId(@PathVariable Integer id) {
        Optional<Puesto> puestoOpt = puestoRepositorio.findById(id);
        return puestoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo puesto
    @PostMapping
    public ResponseEntity<Puesto> crearPuesto(@RequestBody Puesto puesto) {
        Puesto nuevoPuesto = puestoRepositorio.save(puesto);
        return ResponseEntity.ok(nuevoPuesto);
    }

    // Actualizar un puesto existente
    @PutMapping("/{id}")
    public ResponseEntity<Puesto> actualizarPuesto(@PathVariable Integer id, @RequestBody Puesto puestoDetalles) {
        Optional<Puesto> puestoOpt = puestoRepositorio.findById(id);
        if (!puestoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Puesto puesto = puestoOpt.get();
        puesto.setNombrePuesto(puestoDetalles.getNombrePuesto());
        puesto.setDescripcion(puestoDetalles.getDescripcion());
        puesto.setSueldoBase(puestoDetalles.getSueldoBase());

        Puesto puestoActualizado = puestoRepositorio.save(puesto);
        return ResponseEntity.ok(puestoActualizado);
    }

    // Eliminar puesto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPuesto(@PathVariable Integer id) {
        if (!puestoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        puestoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

