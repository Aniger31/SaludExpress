package org.example.saludexpress.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ControladorPagina {
    /*Es para redirigir a la pagina del login */
    @GetMapping("/")
    public String login() {
        return "redirect:/login.html";
    }

}

