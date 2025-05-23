package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    // Buscar por correo (único)
    Optional<Cliente> findByCorreo(String correo);

    // Buscar por teléfono
    List<Cliente> findByTelefono(String telefono);

    // Buscar por nombre exacto
    List<Cliente> findByNombreCliente(String nombreCliente);

    // Buscar por nombre que contenga una cadena (ignorando mayúsculas/minúsculas)
    List<Cliente> findByNombreClienteContainingIgnoreCase(String nombreParcial);

    // Buscar por apellido paterno
    List<Cliente> findByAPaterno(String aPaterno);

    // Buscar por apellido materno
    List<Cliente> findByAMaterno(String aMaterno);

    // Buscar por nombre completo
    List<Cliente> findByNombreClienteAndAPaternoAndAMaterno(String nombre, String aPaterno, String aMaterno);

    // Buscar por nombre o correo
    List<Cliente> findByNombreClienteOrCorreo(String nombreCliente, String correo);

    //estos los podemos cambiar
    // Eliminar por correo
    void deleteByCorreo(String correo);

    // Verificar si existe un cliente por correo
    boolean existsByCorreo(String correo);

}
