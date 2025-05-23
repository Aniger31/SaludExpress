package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuestoRepositorio extends JpaRepository<Puesto, Integer> {
    List<Puesto> findByNombrePuesto(String nombrePuesto);
}
