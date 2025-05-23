package org.example.saludexpress.Repositorios;

import org.example.saludexpress.Modelo_Entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepositorio extends JpaRepository<Medico, Integer> {
    // Buscar médicos por especialidad exacta
    List<Medico> findByEspecialidad(String especialidad);

    // Buscar médicos cuya especialidad contenga texto (ignore mayúsculas/minúsculas)
    List<Medico> findByEspecialidadIgnoreCaseContaining(String especialidad);

    // Buscar médico por cédula profesional exacta
    Medico findByCedulaProfesional(String cedulaProfesional);
}
