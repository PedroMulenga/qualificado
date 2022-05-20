/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.ispi.projectoIspi.Repository.GrupoRepository;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.UsuarioService;
import com.ispi.projectoIspi.model.Grupo;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class CriarContaUsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private MatriculaService matriculaService;
    private Matricula matricula;
    private List<Grupo> grupos = new ArrayList<>();

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal User user, Usuario usuario, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("grupo", grupoRepository.findByNomeIgnoreCase("Estudante"));
        matricula = new Matricula();
        if (user != null) {
            //attribute.addFlashAttribute("info", "Desejamos Boas Vindas ao ISPI Payment Sro(a)" + user.getUsername());
            return new ModelAndView("redirect:/dashboard");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/login")
    public ModelAndView login(@AuthenticationPrincipal User user, Usuario usuario) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("grupo", grupoRepository.findByNomeIgnoreCase("Estudante"));
        matricula = new Matricula();
        if (user != null) {
            return new ModelAndView("redirect:/dashboard");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("listaDeUsuarios", usuarioService.getAll());
        return mv;
    }

    @GetMapping("/criarContaUsuarios")
    public ModelAndView novaContaUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("login");
        //mv.addObject("grupo", grupoRepository.findByNomeIgnoreCase("Estudante"));
        grupos = grupoRepository.findByNomeIgnoreCase("Estudante");
        matricula = new Matricula();
        return mv;
    }

    @PostMapping("/criarContaUsuarios")
    public ModelAndView criarContaUsuarios(@Valid Usuario usuario, BindingResult result, RedirectAttributes attribute) {
        findEstudanteByBi(usuario);
        if (result.hasErrors()) {
            return novaContaUsuario(usuario);
        }
        if (matricula == null) {
            matricula = new Matricula();
            attribute.addFlashAttribute("warning", "Não lhe é permitido criar conta no ISPI Payment, Por favor contacte a secretaria da Instituição");
            return new ModelAndView("redirect:/criarContaUsuarios");
        }
        try {
            usuario.setGrupos(grupos);
            usuario.setMatricula(matricula);
            usuarioService.addNew(usuario);
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        }
        attribute.addFlashAttribute("success", "Conta ISPI Payment criada com sucesso, Faça o seu Login!");
        return new ModelAndView("redirect:/criarContaUsuarios");
    }

    @PostMapping(value = "/verificarUsuario/{bi}{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Matricula findEstudanteByBi(Usuario usuario) {
        matricula = matriculaService.findByBiEstudanteAndSituacao(usuario.getBi(), usuario.getNumeroEstudante());
        return matricula;
    }

    @GetMapping("/403")
    public String acessoNegado() {
        return "error/403";
    }

    /* @GetMapping("/listarAlunos")
    public ModelAndView listarAlunos(Aluno aluno) {
        ModelAndView mv = new ModelAndView("alunos/cadastrarAlunos");
        mv.addObject("listaAlunos", alunoService.getAll());
        mv.addObject("provincias", provinciaService.getAll());
        mv.addObject("municipios", municipioService.getAll());
        mv.addObject("bairros", bairroRepository.findAll());
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
