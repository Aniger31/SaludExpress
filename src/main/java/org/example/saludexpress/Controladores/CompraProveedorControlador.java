package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Compra_Proveedor;
import org.example.saludexpress.Repositorios.Compra_ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras-proveedor")
public class CompraProveedorControlador {

    @Autowired
    private Compra_ProveedorRepositorio compraProveedorRepositorio;

    @GetMapping
    public List<Compra_Proveedor> obtenerTodasLasCompras() {
        return compraProveedorRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra_Proveedor> obtenerCompraPorId(@PathVariable Integer id) {
        Optional<Compra_Proveedor> compra = compraProveedorRepositorio.findById(id);
        return compra.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Compra_Proveedor> crearCompra(@RequestBody Compra_Proveedor compra) {
        Compra_Proveedor nuevaCompra = compraProveedorRepositorio.save(compra);
        return ResponseEntity.ok(nuevaCompra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra_Proveedor> actualizarCompra(@PathVariable Integer id, @RequestBody Compra_Proveedor datosCompra) {
        Optional<Compra_Proveedor> compraOptional = compraProveedorRepositorio.findById(id);
        if (!compraOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Compra_Proveedor compra = compraOptional.get();
        compra.setProveedor(datosCompra.getProveedor());
        compra.setSucursal(datosCompra.getSucursal());
        compra.setFechaCompra(datosCompra.getFechaCompra());

        Compra_Proveedor actualizada = compraProveedorRepositorio.save(compra);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Integer id) {
        if (!compraProveedorRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        compraProveedorRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
