/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.ispi.projectoIspi.Repository.GrupoRepository;
import com.ispi.projectoIspi.Service.FuncionarioService;
import com.ispi.projectoIspi.Service.UsuarioService;
import com.ispi.projectoIspi.model.Funcionario;
import com.ispi.projectoIspi.model.Usuario;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private FuncionarioService funcionarioService;
    private Funcionario funcionario;

    @GetMapping("/cadastroUsuarios")
    public ModelAndView novoUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/usuario");
        mv.addObject("grupos", grupoRepository.findAll());
        return mv;
    }

    @PostMapping("/cadastroUsuarios")
    public ModelAndView cadastroUsuarios(@Valid Usuario usuario, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novoUsuario(usuario);
        }
        try {
            usuario.setFuncionario(funcionario);
            usuarioService.addNew(usuario);
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        }
        attribute.addFlashAttribute("success", "Usuário salvo com sucesso!");
        return new ModelAndView("redirect:/cadastroUsuarios");
    }

    @PostMapping(value = "/usuarioFuncionario/{bi}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Funcionario findFuncionarioByBi(@PathVariable("bi") String bi) {
        funcionario = funcionarioService.findByBiIgnoreCaseAndEstadoIsTrue(bi);
        return funcionario;
    }

    /* @GetMapping("/listarUsuarios")
    public ModelAndView listarAlunos(Usuario usuario) {
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("listaDeUsuarios", usuarioService.getAll());
        return mv;
    }

    @RequestMapping("/getOneAluno")
    @ResponseBody
    public Aluno getOne(Long codigo) {
        return alunoService.getOne(codigo);
    }

    @RequestMapping(value = "/editarAluno", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Aluno aluno, RedirectAttributes attribute) {
        alunoService.update(aluno);
        attribute.addFlashAttribute("success", "Registo actualizado com sucesso!");
        return "redirect:/listarAlunos";

    }

    @RequestMapping(value = "/eliminarAluno", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo, RedirectAttributes attribute) {
        alunoService.delete(codigo);
        attribute.addFlashAttribute("warning", "Registo eliminado com sucesso!");
        return "redirect:/listarAlunos";

    }

    @GetMapping("/aluno/{bi}")
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
