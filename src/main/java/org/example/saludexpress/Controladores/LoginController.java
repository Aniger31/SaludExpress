package org.example.saludexpress.Controladores;

import org.example.saludexpress.Modelo_Entidades.Empleado;
import org.example.saludexpress.Repositorios.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping(path = "/pagina")
public class LoginController {
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @PostMapping(path="/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String,String> loginInfo){
        String nombre = loginInfo.get("nombre");
        String email = loginInfo.get("email");

        List<Empleado> empleado = empleadoRepositorio.findByNombre(nombre);
        if(!empleado.isEmpty()){
            Empleado empleado1 = empleado.getFirst();
            if(empleado1.getCorreo().equals(email)){
                Map<String,Object> respuesta= new HashMap<>();
                respuesta.put("mensaje","Login exitoso");
                respuesta.put("id_empleado",empleado1.getIdEmpleado()); //id que utilizaremos posteriormente
                respuesta.put("rol", empleado1.getPuesto().getNombrePuesto());
                return ResponseEntity.ok(respuesta);
            }

        }
        // creamos la respuesta si no son correctas los datos o credenciales y devuelve la respuesta HTTP
        // que es un estado 401 que es el de que no esta autorizado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", "Credenciales inválidas"));
    }
}
