package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Empleado;
import org.example.saludexpress.Repositorios.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller  // ← Cambiado de @RestController a @Controller
@RequestMapping(path = "/pagina")
public class LoginController {
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    // ← NUEVO: Metodo para mostrar la página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Busca login.html en src/main/resources/templates/
    }

    @PostMapping(path="/login")
    @ResponseBody  // ← Necesario para que este metodo específico devuelva JSON
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String,String> loginInfo){
        String nombre = loginInfo.get("nombre");
        String email = loginInfo.get("email");

        List<Empleado> empleado = empleadoRepositorio.findByNombre(nombre);
        if(!empleado.isEmpty()){
            Empleado empleado1 = empleado.getFirst();
            if(empleado1.getCorreo().equals(email)){
                Map<String,Object> respuesta= new HashMap<>();
                respuesta.put("mensaje","Login exitoso");
                respuesta.put("id_empleado",empleado1.getIdEmpleado());
                respuesta.put("rol", empleado1.getPuesto().getNombrePuesto());
                return ResponseEntity.ok(respuesta);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", "Credenciales inválidas"));
    }
}