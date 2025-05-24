package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Sucursal;
import org.example.saludexpress.Repositorios.SucursalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sucursales")
public class SucursalControlador {

    @Autowired
    private SucursalRepositorio sr;

    @GetMapping
    public List<Sucursal> findAll() {
        return sr.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> findById(@PathVariable Integer id) {
        return sr.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        return ResponseEntity.ok(sr.save(sucursal));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Integer id, @RequestBody Sucursal actualizada) {
        Optional<Sucursal> existente = sr.findById(id);
        if (existente.isPresent()) {
            Sucursal sucursal = existente.get();
            sucursal.setTelefono(actualizada.getTelefono());
            sucursal.setCalle(actualizada.getCalle());
            sucursal.setNumero(actualizada.getNumero());
            sucursal.setColonia(actualizada.getColonia());
            sucursal.setCodigoPostal(actualizada.getCodigoPostal());
            sucursal.setEstado(actualizada.getEstado());
            sucursal.setMunicipio(actualizada.getMunicipio());
            sucursal.setFarmacia(actualizada.getFarmacia());
            return ResponseEntity.ok(sr.save(sucursal));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Integer id) {
        if (sr.existsById(id)) {
            sr.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
