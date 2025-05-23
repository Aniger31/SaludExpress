package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmaciaRepositorio extends JpaRepository<Farmacia, String> {

    // Buscar por razón social
    List<Farmacia> findByRazonSocial(String razonSocial);

    // Buscar por sede central
    List<Farmacia> findBySedeCentral(String sedeCentral);

    // Buscar por correo
    Optional<Farmacia> findByCorreo(String correo);

    // Buscar por teléfono
    List<Farmacia> findByTelefono(String telefono);

    // Verificar si existe una farmacia con cierto nombre
    boolean existsByNombre(String nombre);

}
