package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Venta;
import org.example.saludexpress.Repositorios.VentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaControlador {

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Integer id) {
        Optional<Venta> ventaOpt = ventaRepositorio.findById(id);
        return ventaOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaRepositorio.save(venta);
        return ResponseEntity.ok(nuevaVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Integer id, @RequestBody Venta detallesVenta) {
        Optional<Venta> ventaOpt = ventaRepositorio.findById(id);
        if (!ventaOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Venta venta = ventaOpt.get();
        venta.setCliente(detallesVenta.getCliente());
        venta.setEmpleado(detallesVenta.getEmpleado());
        venta.setFechaVenta(detallesVenta.getFechaVenta());
        venta.setCostoVenta(detallesVenta.getCostoVenta());
        venta.setMetodoPago(detallesVenta.getMetodoPago());

        Venta ventaActualizada = ventaRepositorio.save(venta);
        return ResponseEntity.ok(ventaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id) {
        if (!ventaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ventaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
