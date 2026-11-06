package edu.pucp.gtics.lab8.controller;

// PASO 10: lee el User de la sesion para decidir que lista y que plantilla mostrar.

import edu.pucp.gtics.lab8.entity.*;
import edu.pucp.gtics.lab8.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class JuegosController {

    private final JuegosRepository juegosRepository;
    private final GenerosRepository generosRepository;
    private final PlataformasRepository plataformasRepository;
    private final DistribuidorasRepository distribuidorasRepository;

    public JuegosController(JuegosRepository juegosRepository, GenerosRepository generosRepository,
                            PlataformasRepository plataformasRepository,
                            DistribuidorasRepository distribuidorasRepository) {
        this.juegosRepository = juegosRepository;
        this.generosRepository = generosRepository;
        this.plataformasRepository = plataformasRepository;
        this.distribuidorasRepository = distribuidorasRepository;
    }

    // 2.2.1 Vista pública. Acceso: todos.
    // "/vista" se agrega porque el header del USER enlaza a /vista.
    @GetMapping({"/", "/vista", "/juegos/vista"})
    public String vistaJuegos(Model model, HttpSession session) {
        User usuario = (User) session.getAttribute("usuario");

        if (usuario != null && "USER".equals(usuario.getAutorizacion())) {
            // USER: solo los juegos que no ha comprado (ordenados por nombre desc en la query)
            model.addAttribute("listaJuegos", juegosRepository.obtenerJuegosNoComprados(usuario.getIdusuario()));
        } else {
            // NO logueado (o ADMIN): todos, por nombre descendente
            model.addAttribute("listaJuegos", juegosRepository.findAll(Sort.by(Sort.Direction.DESC, "nombre")));
        }
        return "juegos/vista";
    }

    // 2.3.1 Acceso: ADMIN y USER (cada uno ve una plantilla distinta)
    @GetMapping("/juegos/lista")
    public String listaJuegos(Model model, HttpSession session) {
        User usuario = (User) session.getAttribute("usuario");

        if (usuario != null && "ADMIN".equals(usuario.getAutorizacion())) {
            model.addAttribute("listaJuegos", juegosRepository.findAll(Sort.by(Sort.Direction.ASC, "precio")));
            return "juegos/lista";
        }
        // USER: sus juegos comprados (lista de JuegosUserDto)
        model.addAttribute("listaJuegos", juegosRepository.obtenerJuegosPorUser(usuario.getIdusuario()));
        return "juegos/comprado";
    }

    // ---------------- CRUD (solo ADMIN) ----------------
    @GetMapping("/juegos/editarFrm")
    public String editarJuegos(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Juegos juego = new Juegos();
        // objetos vacíos para que th:field="*{genero.idgenero}" no falle con null
        juego.setGenero(new Generos());
        juego.setPlataforma(new Plataformas());
        juego.setDistribuidora(new Distribuidoras());
        if (id != null) {
            Optional<Juegos> opt = juegosRepository.findById(id);
            if (opt.isEmpty()) return "redirect:/juegos/lista";
            juego = opt.get();
        }
        model.addAttribute("juego", juego);
        cargarCombos(model);
        return "juegos/editarFrm";
    }

    @PostMapping("/juegos/guardar")
    public String guardarJuego(@ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult,
                               Model model, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            cargarCombos(model);
            return "juegos/editarFrm";
        }
        attr.addFlashAttribute("msg", juego.getIdjuego() == 0 ? "Juego creado" : "Juego actualizado");
        juegosRepository.save(juego);
        return "redirect:/juegos/lista";
    }

    @GetMapping("/juegos/borrar")
    public String borrarJuego(@RequestParam("id") int id, RedirectAttributes attr) {
        try {
            juegosRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Juego borrado");
        } catch (DataIntegrityViolationException e) {
            attr.addFlashAttribute("msgError", "No se puede borrar: el juego ya fue comprado por algún usuario");
        }
        return "redirect:/juegos/lista";
    }

    private void cargarCombos(Model model) {
        model.addAttribute("listaGeneros", generosRepository.findAll());
        model.addAttribute("listaPlataformas", plataformasRepository.findAll());
        model.addAttribute("listaDistribuidoras", distribuidorasRepository.findAll());
    }
}
