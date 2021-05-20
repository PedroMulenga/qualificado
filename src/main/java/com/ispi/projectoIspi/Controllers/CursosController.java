/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.model.Cursos;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class CursosController {

    @Autowired
    private CursosService cursosService;

    @GetMapping("/cadastrarCursos")
    public ModelAndView carregarForm(Cursos cursos) {
        ModelAndView mv = new ModelAndView("outros/curso");
        return mv;
    }

    @GetMapping("/listarCursos")
    public ModelAndView listarCursos(Cursos cursos) {
        ModelAndView mv = new ModelAndView("outros/curso");
        mv.addObject("listaCursos", cursosService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarCursos")
    public ModelAndView cadastrarCursos(@Valid @ModelAttribute Cursos cursos, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return carregarForm(cursos);
        }
        try {
            cursosService.addNew(cursos);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarForm(cursos);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return new ModelAndView("redirect:/listarCursos");

    }

    @RequestMapping("/getOneCurso")
    @ResponseBody
    public Optional<Cursos> getOne(Long codigo) {
        return cursosService.getOne(codigo);
    }

    @RequestMapping(value = "/editarCurso", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Cursos curso) {
        cursosService.update(curso);
        return "redirect:/listarCursos";

    }

    @RequestMapping(value = "/eliminarCurso", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        cursosService.delete(codigo);
        return "redirect:/listarCursos";

    }

}
