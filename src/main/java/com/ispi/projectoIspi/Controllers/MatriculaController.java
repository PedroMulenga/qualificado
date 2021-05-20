/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.Situacao;
import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NumeroEstudanteUsuarioExistenteException;
import com.ispi.projectoIspi.Service.AlunoService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.model.Aluno;
import com.ispi.projectoIspi.model.Matricula;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private TurmaService turmasService;
    @Autowired
    private AlunoService alunoService;
    Aluno aluno;

    @GetMapping("/matriculaAluno")
    public ModelAndView novaMatricula(Matricula matricula) {
        ModelAndView mv = new ModelAndView("alunos/matricula");
        mv.addObject("matriculas", matriculaService.getAll());
        mv.addObject("turmas", turmasService.getAll());
        return mv;
    }

    @PostMapping(value = "/aluno/{bi}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Aluno findByBi(@PathVariable("bi") String bi, Situacao situacao) {
        aluno = alunoService.findByBiAndSituacao(bi, situacao.INSCRITO);
        return aluno;
    }

    @PostMapping("/matriculaAluno")
    public ModelAndView matriculaAluno(@Valid @ModelAttribute Matricula matricula,
            BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {

            return novaMatricula(matricula);
        }
        try {
            matricula.setAluno(aluno);
            matriculaService.addNew(matricula);
        } catch (NumeroEstudanteUsuarioExistenteException e) {
            result.rejectValue("numeroEstudante", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (EntidadeUsuarioExistenteException e) {
            result.rejectValue("numeroEstudante", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        }
        attribute.addFlashAttribute("success", "Matrícula feita com sucesso!");
        return new ModelAndView("redirect:matriculaAluno");
    }

    @GetMapping("/listarAlunoMatriculas")
    public ModelAndView listarMatriculas(Matricula matricula) {
        ModelAndView mv = new ModelAndView("alunos/matricula");
        mv.addObject("listaMatriculasAlunos", matriculaService.getAll());
        mv.addObject("listaAlunos", alunoService.getAll());
        return mv;
    }

    @RequestMapping("/getOneAlunoMatricula")
    @ResponseBody
    public Optional<Matricula> getOne(Long codigo) {
        return matriculaService.getOne(codigo);
    }

    @RequestMapping(value = "/editarAlunoMatriculado", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Matricula matricula, RedirectAttributes attribute) {
        matriculaService.update(matricula);
        attribute.addFlashAttribute("success", "Matrícula actualizada com sucesso!");
        return "redirect:/listarAlunoMatriculas";

    }

    @RequestMapping(value = "/eliminarAlunoMatricula", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo, RedirectAttributes attribute) {
        matriculaService.delete(codigo);
        attribute.addFlashAttribute("warning", "Registo eliminado com sucesso!");
        return "redirect:/listarAlunoMatriculas";

    }

}
