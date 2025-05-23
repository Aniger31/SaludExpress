package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepositorio extends JpaRepository<Departamento, Integer> {
    // Buscar por nombre exacto
    List<Departamento> findByNombreDepartamento(String nombreDepartamento);

    // Buscar por nombre que contenga una cadena (ignorando mayúsculas/minúsculas)
    List<Departamento> findByNombreDepartamentoContainingIgnoreCase(String fragmentoNombre);

    // Verificar si ya existe un nombre de departamento
    boolean existsByNombreDepartamento(String nombreDepartamento);

    // Eliminar por nombre
    void deleteByNombreDepartamento(String nombreDepartamento);
}
