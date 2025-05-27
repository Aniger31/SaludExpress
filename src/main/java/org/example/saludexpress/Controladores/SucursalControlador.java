package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Modelo_Entidades.Municipio;
import org.example.saludexpress.Modelo_Entidades.Sucursal;
import org.example.saludexpress.Repositorios.MunicipioRepositorio;
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

    @Autowired
    private MunicipioRepositorio mr;

    @GetMapping
    public List<Sucursal> findAll() {
        return sr.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> findById(@PathVariable Integer id) {
        return sr.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/colonia/{colonia}")
    public ResponseEntity<List<Sucursal>> obtenerPorColonia(@PathVariable String colonia) {
        List<Sucursal> resultado = sr.findByColonia(colonia);
        if (resultado.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/codigo-postal/{cp}")
    public ResponseEntity<List<Sucursal>> obtenerPorCodigoPostal(@PathVariable("cp") String cp) {
        List<Sucursal> resultado = sr.findByCodigoPostal(cp);
        if (resultado.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/municipio/{idMunicipio}")
    public ResponseEntity<List<Sucursal>> obtenerPorMunicipio(@PathVariable Integer idMunicipio) {
        Optional<Municipio> municipio = mr.findById(idMunicipio);
        if (!municipio.isPresent()) return ResponseEntity.badRequest().build();

        List<Sucursal> resultado = sr.findByMunicipio(municipio.get());
        if (resultado.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado);
    }


    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Sucursal>> obtenerPorEstado(@PathVariable Integer idEstado) {
        Estado estado = new Estado();
        estado.setIdEstado(idEstado);

        List<Sucursal> resultado = sr.findByEstado(estado);
        if (resultado.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/farmacia/{nombre}")
    public ResponseEntity<List<Sucursal>> obtenerPorFarmacia(@PathVariable String nombre) {
        List<Sucursal> resultado = sr.findByFarmacia_Nombre(nombre);
        if (resultado.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado);
    }



    @PostMapping
    public ResponseEntity<?> crearSucursal(@RequestBody Sucursal sucursal) {
        if (sucursal.getMunicipio() == null || sucursal.getMunicipio().getIdMunicipio() == null) {
            return ResponseEntity.badRequest().body("Debe especificar un municipio válido.");
        }

        Optional<Municipio> municipio = mr.findById(sucursal.getMunicipio().getIdMunicipio());
        if (!municipio.isPresent()) {
            return ResponseEntity.badRequest().body("El municipio especificado no existe.");
        }

        sucursal.setMunicipio(municipio.get());

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
