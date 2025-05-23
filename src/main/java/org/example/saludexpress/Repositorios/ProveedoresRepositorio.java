package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Modelo_Entidades.Municipio;
import org.example.saludexpress.Modelo_Entidades.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedoresRepositorio extends JpaRepository<Proveedores, Integer> {

    // Buscar proveedor por correo (único)
    Optional<Proveedores> findByCorreo(String correo);

    // Buscar proveedores por nombre (puede haber más de uno con el mismo nombre)
    List<Proveedores> findByNombreProveedorContainingIgnoreCase(String nombre);

    // Buscar proveedores por municipio
    List<Proveedores> findByMunicipio(Municipio municipio);

    // Buscar proveedores por estado
    List<Proveedores> findByEstado(Estado estado);

    // Verificar si ya existe un proveedor con determinado correo
    boolean existsByCorreo(String correo);

    // Eliminar proveedor por correo
    void deleteByCorreo(String correo);
}
