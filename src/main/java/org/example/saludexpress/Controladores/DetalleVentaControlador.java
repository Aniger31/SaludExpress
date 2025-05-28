package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Detalle_Venta;
import org.example.saludexpress.Repositorios.Detalle_VentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaControlador {

    @Autowired
    private Detalle_VentaRepositorio detalleVentaRepositorio;

    @GetMapping
    public List<Detalle_Venta> listarDetallesVenta() {
        return detalleVentaRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Detalle_Venta> obtenerDetallePorId(@PathVariable Integer id) {
        Optional<Detalle_Venta> detalleOpt = detalleVentaRepositorio.findById(id);
        return detalleOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Detalle_Venta> crearDetalleVenta(@RequestBody Detalle_Venta detalle) {
        Detalle_Venta nuevoDetalle = detalleVentaRepositorio.save(detalle);
        return ResponseEntity.ok(nuevoDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Detalle_Venta> actualizarDetalleVenta(@PathVariable Integer id, @RequestBody Detalle_Venta detalles) {
        Optional<Detalle_Venta> detalleOpt = detalleVentaRepositorio.findById(id);
        if (!detalleOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Detalle_Venta detalle = detalleOpt.get();
        detalle.setVenta(detalles.getVenta());
        detalle.setProducto(detalles.getProducto());
        detalle.setCantidad(detalles.getCantidad());
        detalle.setPrecioUnitario(detalles.getPrecioUnitario());

        Detalle_Venta detalleActualizado = detalleVentaRepositorio.save(detalle);
        return ResponseEntity.ok(detalleActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Integer id) {
        if (!detalleVentaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detalleVentaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
