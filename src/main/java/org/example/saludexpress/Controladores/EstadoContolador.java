package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Repositorios.EstadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoContolador {

    @Autowired
    private EstadoRepositorio er;

    //obtener todos los estados
    @GetMapping
    public List<Estado> obtenerTodos() {
        return er.findAll();
    }

    //buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscarPorId(@PathVariable Integer id) {
        return er.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //buscar por nombre exacto
    @GetMapping("/buscarPorNombre")
    public List<Estado> buscarPorNombre(@RequestParam String nombre) {
        return er.findByNombreEstado(nombre);
    }

    //buscar por fragmento de nombre (ignorando mayúsculas)
    @GetMapping("/buscarPorFragmento")
    public List<Estado> buscarPorFragmento(@RequestParam String nombre) {
        return er.findByNombreEstadoContainingIgnoreCase(nombre);
    }

    //buscar por código de estado
    @GetMapping("/buscarPorCodigo")
    public ResponseEntity<Estado> buscarPorCodigo(@RequestParam String codigo) {
        return er.findByCodigoEstado(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //crear nuevo estado
    @PostMapping
    public ResponseEntity<Estado> crear(@RequestBody Estado estado) {
        if (er.existsByCodigoEstado(estado.getCodigoEstado())) {
            return ResponseEntity.badRequest().body(null); // Código duplicado
        }
        return ResponseEntity.ok(er.save(estado));
    }

    //actualizar estado por ID
    @PutMapping("/{id}")
    public ResponseEntity<Estado> actualizar(@PathVariable Integer id, @RequestBody Estado actualizado) {
        Optional<Estado> existente = er.findById(id);
        if (existente.isPresent()) {
            Estado estado = existente.get();
            estado.setNombreEstado(actualizado.getNombreEstado());
            estado.setCodigoEstado(actualizado.getCodigoEstado());
            return ResponseEntity.ok(er.save(estado));
        }
        return ResponseEntity.notFound().build();
    }

    //eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        if (er.existsById(id)) {
            er.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //eliminar por código
    @DeleteMapping("/eliminarPorCodigo")
    public ResponseEntity<Void> eliminarPorCodigo(@RequestParam String codigo) {
        if (er.existsByCodigoEstado(codigo)) {
            er.deleteByCodigoEstado(codigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
