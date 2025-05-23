package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {
    // Buscar por nombre exacto
    List<Estado> findByNombreEstado(String nombreEstado);

    // Buscar por nombre que contenga una cadena (ignorando mayúsculas/minúsculas)
    List<Estado> findByNombreEstadoContainingIgnoreCase(String fragmentoNombre);

    // Buscar por código único
    Optional<Estado> findByCodigoEstado(String codigoEstado);

    // Verificar existencia por código
    boolean existsByCodigoEstado(String codigoEstado);

    // Eliminar por código
    void deleteByCodigoEstado(String codigoEstado);
}
