/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.CursosService;
import com.ispi.projectoIspi.Service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class TurmasController {

    @Autowired
    private TurmaService turmasService;
    @Autowired
    private CursosService cursosService;
    @Autowired
    private MatriculaService matriculaService;

    @GetMapping("/cadastrarTurma")
    public ModelAndView carregarForm(Turma turma) {
        ModelAndView mv = new ModelAndView("outros/turmas");
        mv.addObject("cursos", cursosService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarTurma")
    public ModelAndView cadastrarTurma(@Valid @ModelAttribute Turma turma, BindingResult result, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("redirect:/cadastrarTurma");
        if (result.hasErrors()) {
            return carregarForm(turma);
        }
        try {
            turmasService.addNew(turma);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarForm(turma);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return mv;

    }

    @GetMapping("/listarTurmas")
    public ModelAndView listarTurmas(Turma turma, @PageableDefault(size = 5) Pageable pageable) {
        ModelAndView mv = new ModelAndView("alunos/listaTurmasAlunos");
        Page<Turma> page = turmasService.findAll(pageable);
        List<Turma> listaDeTurmas = page.getContent();
        long totalRegistoPorPaginas = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("totalRegistoPorPaginas", totalRegistoPorPaginas);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeTurmas", listaDeTurmas);
        //mv.addObject("cursos", cursosService.getAll());
        return mv;
    }

    @RequestMapping("/getOneTurma")
    @ResponseBody
    public Turma getOne(Long codigo) {
        return turmasService.getOne(codigo);
    }

    @RequestMapping(value = "/editarTurma", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Turma turma) {
        turmasService.update(turma);
        return "redirect:/listarTurmas";

    }

    @RequestMapping(value = "/eliminarTurma", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        turmasService.delete(codigo);
        return "redirect:/listarTurmas";

    }

    @GetMapping("/{codigo}")
    public ModelAndView detalhesTurmas(@PathVariable("codigo") Long codigo) {
        Turma turmas = turmasService.getOne(codigo);
        ModelAndView mv = new ModelAndView("alunos/detalhesTurmaAlunosMatriculados");
        mv.addObject("turma", turmas);
        Iterable<Matricula> matriculas = matriculaService.findByTurma(turmas);
        mv.addObject("matriculas", matriculas);
        return mv;
    }

}
