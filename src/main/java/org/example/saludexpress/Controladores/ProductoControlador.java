package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Producto;
import org.example.saludexpress.Repositorios.ProductoRepositorio;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {
    @Autowired
    private ProductoRepositorio pR;

    //obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return pR.findAll();
    }

    //obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = pR.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //buscar por nombre
    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return pR.findByNombreProductoContainingIgnoreCase(nombre);
    }

    //buscar por departamento
    @GetMapping("/departamento/{idDepartamento}")
    public List<Producto> buscarPorDepartamento(@PathVariable Integer idDepartamento) {
        return pR.findByDepartamentoIdDepartamento(idDepartamento);
    }

    //buscar por proveedor
    @GetMapping("/proveedor/{idProveedor}")
    public List<Producto> buscarPorProveedor(@PathVariable Integer idProveedor) {
        return pR.findByProveedoresIdProveedor(idProveedor);
    }

    //buscar por marca
    @GetMapping("/marca/{idMarca}")
    public List<Producto> buscarPorMarca(@PathVariable Integer idMarca) {
        return pR.findByMarcaIdMarca(idMarca);
    }

    //crear nuevo producto
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return pR.save(producto);
    }

    //actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Optional<Producto> productoExistente = pR.findById(id);

        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombreProducto(productoActualizado.getNombreProducto());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecioUnitario(productoActualizado.getPrecioUnitario());
            producto.setCantidadDisponible(productoActualizado.getCantidadDisponible());
            producto.setDepartamento(productoActualizado.getDepartamento());
            producto.setProveedor(productoActualizado.getProveedor());
            producto.setMarca(productoActualizado.getMarca());
            return ResponseEntity.ok(pR.save(producto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (pR.existsById(id)) {
            pR.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
