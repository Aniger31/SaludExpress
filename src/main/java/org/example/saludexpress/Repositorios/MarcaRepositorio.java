package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepositorio extends JpaRepository<Marca, Integer> {
    // Buscar marcas por coincidencia parcial de descripción (ignora mayúsculas/minúsculas)
    List<Marca> findByDescripcionContainingIgnoreCase(String descripcion);

    // Verificar si ya existe una marca con determinada descripción exacta
    boolean existsByDescripcionIgnoreCase(String descripcion);
}
