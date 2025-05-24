package org.example.saludexpress.Controladores;
import org.example.saludexpress.Modelo_Entidades.Medico;
import org.example.saludexpress.Repositorios.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
public class MedicoControlador {

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    // Obtener todos los médicos
    @GetMapping
    public List<Medico> listarMedicos() {
        return medicoRepositorio.findAll();
    }

    // Obtener médico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable Integer id) {
        Optional<Medico> medicoOpt = medicoRepositorio.findById(id);
        return medicoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo médico
    @PostMapping
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        // Aquí podrías verificar si el Id_Medico ya está asignado a un Empleado válido
        Medico nuevoMedico = medicoRepositorio.save(medico);
        return ResponseEntity.ok(nuevoMedico);
    }

    // Actualizar médico existente
    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizarMedico(@PathVariable Integer id, @RequestBody Medico detalles) {
        Optional<Medico> medicoOpt = medicoRepositorio.findById(id);
        if (!medicoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Medico medico = medicoOpt.get();
        medico.setEspecialidad(detalles.getEspecialidad());
        medico.setCedulaProfesional(detalles.getCedulaProfesional());

        Medico actualizado = medicoRepositorio.save(medico);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar médico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Integer id) {
        if (!medicoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
