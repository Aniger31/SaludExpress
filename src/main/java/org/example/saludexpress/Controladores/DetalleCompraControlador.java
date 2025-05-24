package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Detalle_Compra;
import org.example.saludexpress.Repositorios.Detalle_CompraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-compra")
public class DetalleCompraControlador {

    @Autowired
    private Detalle_CompraRepositorio detalleCompraRepositorio;

    @GetMapping
    public List<Detalle_Compra> listarDetalles() {
        return detalleCompraRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Detalle_Compra> obtenerDetallePorId(@PathVariable Integer id) {
        Optional<Detalle_Compra> detalle = detalleCompraRepositorio.findById(id);
        return detalle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Detalle_Compra> crearDetalle(@RequestBody Detalle_Compra detalle) {
        Detalle_Compra nuevoDetalle = detalleCompraRepositorio.save(detalle);
        return ResponseEntity.ok(nuevoDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Detalle_Compra> actualizarDetalle(@PathVariable Integer id, @RequestBody Detalle_Compra datosDetalle) {
        Optional<Detalle_Compra> detalleOpt = detalleCompraRepositorio.findById(id);
        if (!detalleOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Detalle_Compra detalle = detalleOpt.get();
        detalle.setCompra(datosDetalle.getCompra());
        detalle.setProducto(datosDetalle.getProducto());
        detalle.setCantidad(datosDetalle.getCantidad());

        Detalle_Compra actualizado = detalleCompraRepositorio.save(detalle);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
        if (!detalleCompraRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detalleCompraRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
