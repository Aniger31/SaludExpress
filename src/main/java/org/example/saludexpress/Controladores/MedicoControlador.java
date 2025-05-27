package org.example.saludexpress.Controladores;
import org.example.saludexpress.Modelo_Entidades.Empleado;
import org.example.saludexpress.Modelo_Entidades.Medico;
import org.example.saludexpress.Repositorios.EmpleadoRepositorio;
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

    @Autowired
    private MedicoRepositorio medicoRepository;

    @Autowired
    private EmpleadoRepositorio empleadoRepository;

    // Crear un nuevo médico
    @PostMapping
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        try {
            // Buscar el empleado existente por ID
            if (medico.getEmpleado() != null && medico.getEmpleado().getIdEmpleado() != null) {
                Empleado empleadoExistente = empleadoRepository.findById(medico.getEmpleado().getIdEmpleado())
                        .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
                medico.setEmpleado(empleadoExistente);
            }

            Medico medicoGuardado = medicoRepository.save(medico);
            return ResponseEntity.ok(medicoGuardado);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
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
