package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Departamento;
import org.example.saludexpress.Repositorios.DepartamentoRepositorio;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoControlador {
    @Autowired
    private DepartamentoRepositorio dr;

    // Obtener todos los departamentos
    @GetMapping
    public List<Departamento> obtenerTodos() {
        return dr.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> buscarPorId(@PathVariable Integer id) {
        return dr.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por nombre exacto
    @GetMapping("/buscarPorNombre")
    public List<Departamento> buscarPorNombre(@RequestParam String nombre) {
        return dr.findByNombreDepartamento(nombre);
    }

    // Buscar por nombre que contenga fragmento
    @GetMapping("/buscarPorFragmento")
    public List<Departamento> buscarPorFragmento(@RequestParam String nombre) {
        return dr.findByNombreDepartamentoContainingIgnoreCase(nombre);
    }

    // Crear un nuevo departamento
    @PostMapping
    public ResponseEntity<Departamento> crear(@RequestBody Departamento departamento) {
        if (dr.existsByNombreDepartamento(departamento.getNombreDepartamento())) {
            return ResponseEntity.badRequest().build(); // Ya existe
        }
        return ResponseEntity.ok(dr.save(departamento));
    }

    // Actualizar departamento
    @PutMapping("/{id}")
    public ResponseEntity<Departamento> actualizar(@PathVariable Integer id, @RequestBody Departamento actualizado) {
        Optional<Departamento> existente = dr.findById(id);
        if (existente.isPresent()) {
            Departamento departamento = existente.get();
            departamento.setNombreDepartamento(actualizado.getNombreDepartamento());
            return ResponseEntity.ok(dr.save(departamento));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (dr.existsById(id)) {
            dr.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar por nombre
    @DeleteMapping("/eliminarPorNombre")
    public ResponseEntity<Void> eliminarPorNombre(@RequestParam String nombre) {
        if (dr.existsByNombreDepartamento(nombre)) {
            dr.deleteByNombreDepartamento(nombre);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
