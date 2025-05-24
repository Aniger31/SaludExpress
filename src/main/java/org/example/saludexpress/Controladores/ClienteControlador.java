package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Cliente;
import org.example.saludexpress.Repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Obtener todos los clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepositorio.findAll();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        System.out.println("Recibido cliente con correo: " + cliente.getCorreo());
        if (clienteRepositorio.existsByCorreo(cliente.getCorreo())) {
            System.out.println("El correo ya existe!");
            return ResponseEntity.badRequest().build(); // Ya existe un cliente con ese correo
        }
        Cliente nuevoCliente = clienteRepositorio.save(cliente);
        System.out.println("Cliente guardado con ID: " + nuevoCliente.getIdCliente() );
        return ResponseEntity.ok(nuevoCliente);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteDetalles) {
        Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
        if (!clienteOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOpt.get();
        cliente.setNombreCliente(clienteDetalles.getNombreCliente());
        cliente.setaPaterno(clienteDetalles.getaPaterno());
        cliente.setaMaterno(clienteDetalles.getaMaterno());
        cliente.setCorreo(clienteDetalles.getCorreo());
        cliente.setTelefono(clienteDetalles.getTelefono());
        Cliente clienteActualizado = clienteRepositorio.save(cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    // Eliminar cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        if (!clienteRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar cliente por correo
    @DeleteMapping("/correo/{correo}")
    public ResponseEntity<Void> eliminarClientePorCorreo(@PathVariable String correo) {
        if (!clienteRepositorio.existsByCorreo(correo)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepositorio.deleteByCorreo(correo);
        return ResponseEntity.noContent().build();
    }

    // Buscar cliente por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Cliente> buscarPorCorreo(@PathVariable String correo) {
        Optional<Cliente> clienteOpt = clienteRepositorio.findByCorreo(correo);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Buscar clientes por nombre parcial
    @GetMapping("/nombre")
    public List<Cliente> buscarPorNombreParcial(@RequestParam String nombre) {
        return clienteRepositorio.findByNombreClienteContainingIgnoreCase(nombre);
    }
}
