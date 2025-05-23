package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Estado;
import org.example.saludexpress.Modelo_Entidades.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipioRepositorio extends JpaRepository<Municipio, Integer> {
    // Buscar municipios por nombre exacto
    List<Municipio> findByNombreMunicipio(String nombreMunicipio);

    // Buscar municipios que contengan parte del nombre (ignorando mayúsculas/minúsculas)
    List<Municipio> findByNombreMunicipioContainingIgnoreCase(String fragmentoNombre);

    // Buscar municipios por estado
    List<Municipio> findByEstado(Estado estado);

    // Buscar un municipio por nombre y estado (clave única compuesta)
    Optional<Municipio> findByNombreMunicipioAndEstado(String nombreMunicipio, Estado estado);

    // Verificar existencia de un municipio por nombre y estado
    boolean existsByNombreMunicipioAndEstado(String nombreMunicipio, Estado estado);

    // Eliminar municipio por nombre y estado
    void deleteByNombreMunicipioAndEstado(String nombreMunicipio, Estado estado);
}
