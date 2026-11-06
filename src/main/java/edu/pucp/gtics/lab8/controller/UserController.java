package edu.pucp.gtics.lab8.controller;

// PASO 8: login (solo el GET del formulario) y signInRedirect, que guarda datos en SESION.

import edu.pucp.gtics.lab8.entity.Juegos;
import edu.pucp.gtics.lab8.entity.User;
import edu.pucp.gtics.lab8.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // PASO 8a: muestra el formulario de login (enunciado 1.2).
    // OJO: aquí NO se procesa el login; el POST a /processLogin lo atiende Spring Security.
    @GetMapping("/signIn")
    public String signIn(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {   // si ya está logueado, no tiene sentido volver al login
            return "redirect:/juegos/lista";
        }
        return "user/signIn";
    }

    // PASO 8b: Spring llega aquí después de un login exitoso (defaultSuccessUrl)
    @GetMapping("/signInRedirect")
    public String signInRedirect(Authentication auth, HttpSession session) {
        // auth.getName() = el "username" con el que se logueó = correo
        User usuario = userRepository.findByCorreo(auth.getName());

        // Guardamos el usuario en SESIÓN -> disponible en todos los controllers y vistas
        session.setAttribute("usuario", usuario);

        // Variables de sesión del carrito (obligatorias según el enunciado)
        List<Juegos> carrito = new ArrayList<>();
        session.setAttribute("carrito", carrito);
        session.setAttribute("ncarrito", 0);

        return "redirect:/juegos/lista";
    }
}
