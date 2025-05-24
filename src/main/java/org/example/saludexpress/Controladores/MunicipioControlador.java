package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Modelo_Entidades.Municipio;
import org.example.saludexpress.Repositorios.EstadoRepositorio;
import org.example.saludexpress.Repositorios.MunicipioRepositorio;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/municipios")
public class MunicipioControlador {

    @Autowired
    private MunicipioRepositorio munR;

    @Autowired
    private EstadoRepositorio esR;

    //listar todos los municipios
    @GetMapping
    public List<Municipio> obtenerTodos() {
        return munR.findAll();
    }

    //obtener municipio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Municipio> obtenerPorId(@PathVariable int id) {
        Optional<Municipio> municipio = munR.findById(id);
        return municipio.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //buscar por nombre exacto
    @GetMapping("/nombre")
    public List<Municipio> buscarPorNombre(@RequestParam String nombre) {
        return munR.findByNombreMunicipio(nombre);
    }

    //bscar por fragmento del nombre
    @GetMapping("/buscar")
    public List<Municipio> buscarPorNombreParcial(@RequestParam String nombre) {
        return munR.findByNombreMunicipioContainingIgnoreCase(nombre);
    }

    //bscar municipios por ID del estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Municipio>> buscarPorEstado(@PathVariable int idEstado) {
        Optional<Estado> estado = esR.findById(idEstado);
        if (!estado.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(munR.findByEstado(estado.get()));
    }

    //crear un nuevo municipio
    @PostMapping
    public ResponseEntity<?> crearMunicipio(@RequestBody Municipio nuevoMunicipio) {
        if (nuevoMunicipio.getEstado() == null || nuevoMunicipio.getEstado().getIdEstado() == null){
            return ResponseEntity.badRequest().body("Debe especificar un estado válido.");
        }

        Optional<Estado> estado = esR.findById(nuevoMunicipio.getEstado().getIdEstado());
        if (!estado.isPresent()) {
            return ResponseEntity.badRequest().body("El estado especificado no existe.");
        }

        if (munR.existsByNombreMunicipioAndEstado(nuevoMunicipio.getNombreMunicipio(), estado.get())) {
            return ResponseEntity.badRequest().body("Ya existe un municipio con ese nombre en ese estado.");
        }

        nuevoMunicipio.setEstado(estado.get());
        Municipio guardado = munR.save(nuevoMunicipio);
        return ResponseEntity.ok(guardado);
    }

    //actualizar municipio por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMunicipio(@PathVariable int id, @RequestBody Municipio datosActualizados) {
        Optional<Municipio> municipioOpt = munR.findById(id);
        if (!municipioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Municipio municipio = municipioOpt.get();

        if (datosActualizados.getEstado() != null) {
            Optional<Estado> estado = esR.findById(datosActualizados.getEstado().getIdEstado());
            if (!estado.isPresent()) {
                return ResponseEntity.badRequest().body("Estado no válido.");
            }
            municipio.setEstado(estado.get());
        }

        municipio.setNombreMunicipio(datosActualizados.getNombreMunicipio());
        munR.save(municipio);
        return ResponseEntity.ok(municipio);
    }

    //eliminar municipio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable int id) {
        if (!munR.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        munR.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //eliminar municipio por nombre y estado
    @DeleteMapping("/por-nombre-estado")
    public ResponseEntity<?> eliminarPorNombreYEstado(@RequestParam String nombre, @RequestParam int idEstado) {
        Optional<Estado> estadoOpt = esR.findById(idEstado);
        if (!estadoOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Estado no encontrado.");
        }

        if (!munR.existsByNombreMunicipioAndEstado(nombre, estadoOpt.get())) {
            return ResponseEntity.notFound().build();
        }

        munR.deleteByNombreMunicipioAndEstado(nombre, estadoOpt.get());
        return ResponseEntity.ok().build();
    }
}
