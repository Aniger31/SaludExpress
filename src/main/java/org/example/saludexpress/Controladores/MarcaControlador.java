package org.example.saludexpress.Controladores;


import org.example.saludexpress.Modelo_Entidades.Marca;
import org.example.saludexpress.Repositorios.MarcaRepositorio;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marcas")
public class MarcaControlador {
    @Autowired
    private MarcaRepositorio mr;

    //obtener todas las marcas
    @GetMapping
    public List<Marca> obtenerTodasLasMarcas() {
        return mr.findAll();
    }

    //buscar marcas por descripción parcial
    @GetMapping("/buscar")
    public List<Marca> buscarPorDescripcion(@RequestParam String descripcion) {
        return mr.findByDescripcionContainingIgnoreCase(descripcion);
    }

    //obtener marca por ID
    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable Integer id) {
        Optional<Marca> marca = mr.findById(id);
        return marca.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //crear nueva marca
    @PostMapping
    public ResponseEntity<?> crearMarca(@RequestBody Marca nuevaMarca) {
        if (mr.existsByDescripcionIgnoreCase(nuevaMarca.getDescripcion())) {
            return ResponseEntity.badRequest().body("Ya existe una marca con esa descripción.");
        }
        Marca marcaGuardada = mr.save(nuevaMarca);
        return ResponseEntity.ok(marcaGuardada);
    }

    //actualizar una marca existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMarca(@PathVariable Integer id, @RequestBody Marca marcaActualizada) {
        Optional<Marca> marcaExistente = mr.findById(id);

        if (!marcaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Marca marca = marcaExistente.get();
        marca.setDescripcion(marcaActualizada.getDescripcion());
        mr.save(marca);
        return ResponseEntity.ok(marca);
    }

    //eliminar marca por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMarca(@PathVariable Integer id) {
        if (!mr.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mr.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
