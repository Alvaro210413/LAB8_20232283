package edu.pucp.gtics.lab8.controller;

import edu.pucp.gtics.lab8.entity.Distribuidoras;
import edu.pucp.gtics.lab8.entity.Paises;
import edu.pucp.gtics.lab8.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab8.repository.PaisesRepository;
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
@RequestMapping("/distribuidoras")
public class DistribuidorasController {

    private final DistribuidorasRepository distribuidorasRepository;
    private final PaisesRepository paisesRepository;

    public DistribuidorasController(DistribuidorasRepository distribuidorasRepository, PaisesRepository paisesRepository) {
        this.distribuidorasRepository = distribuidorasRepository;
        this.paisesRepository = paisesRepository;
    }

    @GetMapping("/lista")
    public String listaDistribuidoras(Model model) {
        model.addAttribute("listaDistribuidoras", distribuidorasRepository.findAll());
        return "distribuidoras/lista";
    }

    @GetMapping("/editarFrm")
    public String editarDistribuidoras(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Distribuidoras distribuidora = new Distribuidoras();
        distribuidora.setSede(new Paises());   // para que th:field="*{sede.idpais}" no falle con null
        if (id != null) {
            Optional<Distribuidoras> opt = distribuidorasRepository.findById(id);
            if (opt.isEmpty()) return "redirect:/distribuidoras/lista";
            distribuidora = opt.get();
        }
        model.addAttribute("distribuidora", distribuidora);
        model.addAttribute("listaPaises", paisesRepository.findAll());
        return "distribuidoras/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora(@ModelAttribute("distribuidora") @Valid Distribuidoras distribuidora,
                                       BindingResult bindingResult, Model model, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listaPaises", paisesRepository.findAll());
            return "distribuidoras/editarFrm";
        }
        attr.addFlashAttribute("msg", distribuidora.getIddistribuidora() == 0 ? "Distribuidora creada" : "Distribuidora actualizada");
        distribuidorasRepository.save(distribuidora);
        return "redirect:/distribuidoras/lista";
    }

    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id, RedirectAttributes attr) {
        try {
            distribuidorasRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Distribuidora borrada");
        } catch (DataIntegrityViolationException e) {
            attr.addFlashAttribute("msgError", "No se puede borrar: hay juegos de esta distribuidora");
        }
        return "redirect:/distribuidoras/lista";
    }
}
