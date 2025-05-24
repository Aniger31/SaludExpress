package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Producto_Receta;
import org.example.saludexpress.Repositorios.Producto_RecetaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos-receta")
public class ProductoRecetaControlador {

    @Autowired
    private Producto_RecetaRepositorio productoRecetaRepositorio;

    @GetMapping
    public List<Producto_Receta> listarProductosReceta() {
        return productoRecetaRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto_Receta> obtenerProductoRecetaPorId(@PathVariable Integer id) {
        Optional<Producto_Receta> productoRecetaOpt = productoRecetaRepositorio.findById(id);
        return productoRecetaOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto_Receta> crearProductoReceta(@RequestBody Producto_Receta productoReceta) {
        Producto_Receta nuevoProductoReceta = productoRecetaRepositorio.save(productoReceta);
        return ResponseEntity.ok(nuevoProductoReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto_Receta> actualizarProductoReceta(@PathVariable Integer id, @RequestBody Producto_Receta detallesProductoReceta) {
        Optional<Producto_Receta> productoRecetaOpt = productoRecetaRepositorio.findById(id);
        if (!productoRecetaOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Producto_Receta productoReceta = productoRecetaOpt.get();
        productoReceta.setReceta(detallesProductoReceta.getReceta());
        productoReceta.setProducto(detallesProductoReceta.getProducto());
        productoReceta.setDosis(detallesProductoReceta.getDosis());

        Producto_Receta productoRecetaActualizado = productoRecetaRepositorio.save(productoReceta);
        return ResponseEntity.ok(productoRecetaActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProductoReceta(@PathVariable Integer id) {
        if (!productoRecetaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRecetaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
