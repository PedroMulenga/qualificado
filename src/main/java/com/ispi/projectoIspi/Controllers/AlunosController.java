/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.BairroRepository;
import com.ispi.projectoIspi.Service.AlunoService;
import com.ispi.projectoIspi.Service.MunicipioService;
import com.ispi.projectoIspi.Service.ProvinciaService;
import com.ispi.projectoIspi.model.Aluno;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class AlunosController {

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private BairroRepository bairroRepository;

    @GetMapping("/cadastroAlunos")
    public ModelAndView novoAluno(Aluno aluno) {
        ModelAndView mv = new ModelAndView("alunos/cadastrarAlunos");
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("municipios", municipioService.getAll());
        mv.addObject("bairros", bairroRepository.findAll());
        return mv;
    }

    @PostMapping("/cadastroAlunos")
    public ModelAndView cadastroAlunos(@Valid @ModelAttribute Aluno aluno, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novoAluno(aluno);
        }
        try {
            if (aluno.getCodigo() != null) {
                alunoService.update(aluno);
                attribute.addFlashAttribute("success", "Registo actualizado com sucesso!");
            } else {
                alunoService.addNew(aluno);
                attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
            }
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novoAluno(aluno);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novoAluno(aluno);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return novoAluno(aluno);
        }
        return new ModelAndView("redirect:/cadastroAlunos");
    }

    @RequestMapping("/listarAlunos")
    public ModelAndView listarAlunos(Model model) {

        return listarAlunos(1);
    }

    @GetMapping("/listarAlunos/{pageNumber}")
    public ModelAndView listarAlunos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("alunos/listaAlunos");
        Page<Aluno> page = alunoService.findAll(paginaCorrente);
        List<Aluno> listaDeAlunos = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaAlunos", listaDeAlunos);

        return mv;
    }

    /*@RequestMapping("/getOneAluno")
    @ResponseBody
    public Aluno getOne(Long codigo) {
        return alunoService.getOne(codigo);
    }*/
    @GetMapping("/editarAluno/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("alunos/cadastrarAlunos");
        Optional<Aluno> alunoOptional = alunoService.getOne(codigo);
        mv.addObject("aluno", alunoOptional);
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("municipios", municipioService.getAll());
        mv.addObject("bairros", bairroRepository.findAll());
        return mv;

    }

    @RequestMapping(value = "/eliminarAluno", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo, RedirectAttributes attribute) {
        alunoService.delete(codigo);
        attribute.addFlashAttribute("warning", "Registo eliminado com sucesso!");
        return "redirect:/listarAlunos";

    }

    /*    @GetMapping("/aluno/{bi}")
    public ModelAndView findByBi(@PathVariable("bi") String bi) {
        ModelAndView mv = new ModelAndView("alunos/texte");
        Aluno aluno = alunoService.findByBi(bi);
        mv.addObject("aluno", aluno);
        return mv;

    }

    @GetMapping("/buscar")
    public String buscarAluno() {
        return "alunos/texte";

    }

    @PostMapping("/buscar")
    public ModelAndView mostrarDados(@ModelAttribute Aluno aluno, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("alunos/texte");
        aluno = alunoService.findByBi(aluno.getBi());
        mv.addObject("aluno", aluno);
        if (aluno == null) {
            aluno = new Aluno();
            attribute.addFlashAttribute("error", "Aluno não inscrito/ou cancelado!");
        } else {
            attribute.addFlashAttribute("success", "Dados do Aluno encontrado!");
        }
        return mv;

    }*/
}
