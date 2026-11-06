package edu.pucp.gtics.lab8.controller;

// PASO 11: el carrito es una List<Juegos> en la SESION; aqui se anade, borra y compra.

import edu.pucp.gtics.lab8.entity.Juegos;
import edu.pucp.gtics.lab8.entity.JuegosxUsuario;
import edu.pucp.gtics.lab8.entity.User;
import edu.pucp.gtics.lab8.repository.JuegosRepository;
import edu.pucp.gtics.lab8.repository.JuegosxUsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// Todo /carrito/** es solo para USER (ver WebSecurityConfig)
@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final JuegosRepository juegosRepository;
    private final JuegosxUsuarioRepository juegosxUsuarioRepository;

    public CarritoController(JuegosRepository juegosRepository, JuegosxUsuarioRepository juegosxUsuarioRepository) {
        this.juegosRepository = juegosRepository;
        this.juegosxUsuarioRepository = juegosxUsuarioRepository;
    }

    // Lee la variable de sesión "carrito" (la crea si no existe)
    @SuppressWarnings("unchecked")
    private List<Juegos> obtenerCarrito(HttpSession session) {
        List<Juegos> carrito = (List<Juegos>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    // Cada vez que cambia el carrito, se actualiza "ncarrito" (el numerito del ícono)
    private void actualizarSesion(HttpSession session, List<Juegos> carrito) {
        session.setAttribute("carrito", carrito);
        session.setAttribute("ncarrito", carrito.size());
    }

    // 2.4.1 Lista el carrito ordenado por precio ascendente
    @GetMapping("/lista")
    public String listaCarrito(Model model, HttpSession session) {
        List<Juegos> carrito = obtenerCarrito(session);
        carrito.sort(Comparator.comparing(Juegos::getPrecio));
        actualizarSesion(session, carrito);
        model.addAttribute("listaCarrito", carrito);
        return "carrito/lista";
    }

    // 2.4.2 Añadir: registra el juego en la variable de sesión "carrito" y actualiza "ncarrito"
    @GetMapping("/anadir")
    public String anadirCarrito(@RequestParam("id") int id, HttpSession session) {
        List<Juegos> carrito = obtenerCarrito(session);
        Optional<Juegos> opt = juegosRepository.findById(id);

        // se añade solo si existe y no está ya en el carrito
        boolean yaEsta = carrito.stream().anyMatch(j -> j.getIdjuego() == id);
        if (opt.isPresent() && !yaEsta) {
            carrito.add(opt.get());
        }
        actualizarSesion(session, carrito);
        return "redirect:/vista";
    }

    // 2.4.1 Borrar un juego del carrito
    @GetMapping("/borrar")
    public String borrarCarrito(@RequestParam("id") int id, HttpSession session) {
        List<Juegos> carrito = obtenerCarrito(session);
        carrito.removeIf(j -> j.getIdjuego() == id);
        actualizarSesion(session, carrito);
        return "redirect:/carrito/lista";
    }

    // 2.4.1 Comprar: inserta cada juego del carrito en juegosxusuario (cantidad 1) y vacía el carrito
    @GetMapping("/compra")
    public String comprarCarrito(HttpSession session) {
        User usuario = (User) session.getAttribute("usuario");
        List<Juegos> carrito = obtenerCarrito(session);

        for (Juegos juego : carrito) {
            JuegosxUsuario jxu = new JuegosxUsuario();
            jxu.setJuego(juego);
            jxu.setUsuario(usuario);
            jxu.setCantidad(1);
            juegosxUsuarioRepository.save(jxu);
        }

        actualizarSesion(session, new ArrayList<>());   // carrito vacío, ncarrito = 0
        return "redirect:/juegos/lista";
    }
}
