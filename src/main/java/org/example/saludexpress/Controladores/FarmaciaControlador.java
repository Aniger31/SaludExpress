package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Farmacia;
import org.example.saludexpress.Repositorios.FarmaciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmacia")
public class FarmaciaControlador {
    @Autowired
    private FarmaciaRepositorio fr;

    @GetMapping
    public List<Farmacia> findAll() {
        return fr.findAll();
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Farmacia> findByNombre(@PathVariable String nombre) {
        return fr.findById(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Farmacia> create(@RequestBody Farmacia farmacia) {
        if(fr.existsByNombre(farmacia.getNombre())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fr.save(farmacia));
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<Farmacia> actualizar(@PathVariable String nombre,@RequestBody Farmacia farmaciaAct) {
        Optional<Farmacia> f = fr.findById(nombre);
        if(f.isPresent()) {
            Farmacia farmaciaActual = f.get();
            farmaciaActual.setNombre(farmaciaAct.getNombre());
            farmaciaActual.setRazonSocial(farmaciaAct.getRazonSocial());
            farmaciaActual.setSedeCentral(farmaciaAct.getSedeCentral());
            farmaciaActual.setTelefono(farmaciaAct.getTelefono());
            farmaciaActual.setCorreo(farmaciaAct.getCorreo());
            return ResponseEntity.ok(fr.save(farmaciaActual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Farmacia> eliminar(@PathVariable String nombre) {
        if(fr.existsByNombre(nombre)) {
            fr.deleteById(nombre);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
