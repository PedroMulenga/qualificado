/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;

import com.qualificador.ExceptionMessages.NomeExistenteException;
import com.qualificador.Service.FormacaoAcademicaService;
import com.qualificador.Service.UsuarioService;
import com.qualificador.model.FormacaoAcademica;
import com.qualificador.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/formacoes")
public class FormacaoAcademicaController {

    @Autowired
    private FormacaoAcademicaService formacaoAcademicaService;
    private Optional<Usuario> usuarioSistema;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listarformacoesAcademicas")
    public ModelAndView listarFormacaoAcademica(@AuthenticationPrincipal User user, FormacaoAcademica formacaoAcademica) {
        ModelAndView mv = new ModelAndView("formacoes/listaFormacoesAcademicas");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        mv.addObject("listaFormacoesProfissionais", formacaoAcademicaService.findByUsuario(usuarioSistema.get()));
        return mv;
    }

    @GetMapping("/cadastrarFormacaoAcademica")
    public ModelAndView carregarForm(@AuthenticationPrincipal User user, FormacaoAcademica formacaoAcademica) {
        ModelAndView mv = new ModelAndView("formacoes/formacaoAcademica");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        mv.addObject("usuario", usuarioSistema);
        return mv;
    }

    @PostMapping("/cadastrarFormacaoAcademica")
    public ModelAndView cadastrarFormacaoAcademica(@Valid @ModelAttribute FormacaoAcademica formacaoAcademica, BindingResult result,
            RedirectAttributes attribute, @AuthenticationPrincipal User user, Usuario usuario) {
        usuario = usuarioSistema.get();
        if (result.hasErrors()) {
            return carregarForm(user, formacaoAcademica);
        }

        try {
            formacaoAcademica.setUsuario(usuario);
            formacaoAcademicaService.addNew(formacaoAcademica);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarForm(user, formacaoAcademica);
        }
        attribute.addFlashAttribute("success", "Formacao Académica Adicionada com Sucesso!");
        return new ModelAndView("redirect:/formacoes/cadastrarFormacaoAcademica");

    }

    @GetMapping("/editarFormacaoAcademica/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("formacoes/formacaoAcademica");
        Optional<FormacaoAcademica> grupoOptional = formacaoAcademicaService.getOne(codigo);
        mv.addObject("formacaoAcademica", grupoOptional);
        return mv;

    }

    @GetMapping("/eliminarFormacaoAcademica/{codigo}")
    public ModelAndView deleteFormacaoAcademica(@PathVariable("codigo") Long codigo) {
        formacaoAcademicaService.delete(codigo);
        return new ModelAndView("redirect:/formacoes/listarformacoesAcademicas");

    }

}
