package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Receta;
import org.example.saludexpress.Repositorios.RecetaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaControlador {

    @Autowired
    private RecetaRepositorio recetaRepositorio;

    @GetMapping
    public List<Receta> listarRecetas() {
        return recetaRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable Integer id) {
        Optional<Receta> recetaOpt = recetaRepositorio.findById(id);
        return recetaOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Receta> crearReceta(@RequestBody Receta receta) {
        Receta nuevaReceta = recetaRepositorio.save(receta);
        return ResponseEntity.ok(nuevaReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable Integer id, @RequestBody Receta detallesReceta) {
        Optional<Receta> recetaOpt = recetaRepositorio.findById(id);
        if (!recetaOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Receta receta = recetaOpt.get();
        receta.setMedico(detallesReceta.getMedico());
        receta.setCliente(detallesReceta.getCliente());
        receta.setFechaReceta(detallesReceta.getFechaReceta());
        receta.setObservaciones(detallesReceta.getObservaciones());

        Receta recetaActualizada = recetaRepositorio.save(receta);
        return ResponseEntity.ok(recetaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Integer id) {
        if (!recetaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        recetaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
