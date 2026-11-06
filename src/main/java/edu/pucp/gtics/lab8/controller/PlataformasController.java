package edu.pucp.gtics.lab8.controller;

import edu.pucp.gtics.lab8.entity.Plataformas;
import edu.pucp.gtics.lab8.repository.PlataformasRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

// Solo ADMIN (ver WebSecurityConfig)
@Controller
@RequestMapping("/plataformas")
public class PlataformasController {

    private final PlataformasRepository plataformasRepository;

    public PlataformasController(PlataformasRepository plataformasRepository) {
        this.plataformasRepository = plataformasRepository;
    }

    @GetMapping("/lista")
    public String listaPlataformas(Model model) {
        model.addAttribute("listaPlataformas", plataformasRepository.findAll());
        return "plataformas/lista";
    }

    @GetMapping("/editarFrm")
    public String editarPlataformas(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Plataformas plataforma = new Plataformas();
        if (id != null) {
            Optional<Plataformas> opt = plataformasRepository.findById(id);
            if (opt.isEmpty()) return "redirect:/plataformas/lista";
            plataforma = opt.get();
        }
        model.addAttribute("plataforma", plataforma);
        return "plataformas/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarPlataforma(@ModelAttribute("plataforma") @Valid Plataformas plataforma,
                                    BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            return "plataformas/editarFrm";
        }
        attr.addFlashAttribute("msg", plataforma.getIdplataforma() == 0 ? "Plataforma creada" : "Plataforma actualizada");
        plataformasRepository.save(plataforma);
        return "redirect:/plataformas/lista";
    }

    @GetMapping("/borrar")
    public String borrarPlataforma(@RequestParam("id") int id, RedirectAttributes attr) {
        try {
            plataformasRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Plataforma borrada");
        } catch (DataIntegrityViolationException e) {
            attr.addFlashAttribute("msgError", "No se puede borrar: hay juegos de esta plataforma");
        }
        return "redirect:/plataformas/lista";
    }
}
