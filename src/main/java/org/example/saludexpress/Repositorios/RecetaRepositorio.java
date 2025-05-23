package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, Integer> {
    // Buscar recetas por id de médico
    List<Receta> findByMedicoIdMedico(Integer idMedico);

    // Buscar recetas por id de cliente
    List<Receta> findByClienteIdCliente(Integer idCliente);

    // Buscar recetas por fecha exacta
    List<Receta> findByFechaReceta(Date fecha);

    // Buscar recetas entre un rango de fechas
    List<Receta> findByFechaRecetaBetween(Date fechaInicio, Date fechaFin);
}
