package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Modelo_Entidades.Municipio;
import org.example.saludexpress.Modelo_Entidades.Proveedores;
import org.example.saludexpress.Repositorios.EstadoRepositorio;
import org.example.saludexpress.Repositorios.MunicipioRepositorio;
import org.example.saludexpress.Repositorios.ProveedoresRepositorio;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorControlador {
    @Autowired
    private ProveedoresRepositorio proR;

    @Autowired
    private MunicipioRepositorio mR;

    @Autowired
    private EstadoRepositorio eR;

    //obtener todos los proveedores
    @GetMapping
    public List<Proveedores> listarProveedores() {
        return proR.findAll();
    }

    //obtener proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedores> obtenerProveedorPorId(@PathVariable Integer id) {
        Optional<Proveedores> proveedor = proR.findById(id);
        return proveedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //crear nuevo proveedor
    @PostMapping
    public ResponseEntity<Proveedores> crearProveedor(@RequestBody Proveedores proveedor) {
        if (proR.existsByCorreo(proveedor.getCorreo())) {
            return ResponseEntity.badRequest().body(null); // correo ya existente
        }
        return ResponseEntity.ok(proR.save(proveedor));
    }

    //actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedores> actualizarProveedor(@PathVariable Integer id, @RequestBody Proveedores proveedorActualizado) {
        Optional<Proveedores> proveedorExistente = proR.findById(id);
        if (proveedorExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Proveedores proveedor = proveedorExistente.get();
        proveedor.setNombreProveedor(proveedorActualizado.getNombreProveedor());
        proveedor.setApaterno(proveedorActualizado.getApaterno());
        proveedor.setAmaterno(proveedorActualizado.getAmaterno());
        proveedor.setCalle(proveedorActualizado.getCalle());
        proveedor.setColonia(proveedorActualizado.getColonia());
        proveedor.setCodigoPostal(proveedorActualizado.getCodigoPostal());
        proveedor.setTelefono(proveedorActualizado.getTelefono());
        proveedor.setCorreo(proveedorActualizado.getCorreo());
        proveedor.setMunicipio(proveedorActualizado.getMunicipio());
        proveedor.setEstado(proveedorActualizado.getEstado());

        return ResponseEntity.ok(proR.save(proveedor));
    }

    //eliminar proveedor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Integer id) {
        if (!proR.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proR.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //buscar proveedores por nombre (parcial e insensible a mayúsculas)
    @GetMapping("/buscar/nombre")
    public List<Proveedores> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return proR.findByNombreProveedorContainingIgnoreCase(nombre);
    }

    //buscar proveedor por correo
    @GetMapping("/buscar/correo")
    public ResponseEntity<Proveedores> buscarPorCorreo(@RequestParam("correo") String correo) {
        Optional<Proveedores> proveedor = proR.findByCorreo(correo);
        return proveedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //buscar proveedores por municipio
    @GetMapping("/buscar/municipio/{idMunicipio}")
    public ResponseEntity<List<Proveedores>> buscarPorMunicipio(@PathVariable Integer idMunicipio) {
        Optional<Municipio> municipio = mR.findById(idMunicipio);
        if (municipio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proR.findByMunicipio(municipio.get()));
    }

    //buscar proveedores por estado
    @GetMapping("/buscar/estado/{idEstado}")
    public ResponseEntity<List<Proveedores>> buscarPorEstado(@PathVariable Integer idEstado) {
        Optional<Estado> estado = eR.findById(idEstado);
        if (estado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proR.findByEstado(estado.get()));
    }

}

