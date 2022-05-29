/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;

import com.qualificador.ExceptionMessages.NomeExistenteException;
import com.qualificador.Service.CertificacaoService;
import com.qualificador.Service.ExperienciaProfissionalService;
import com.qualificador.Service.UsuarioService;
import com.qualificador.model.Certificacao;
import com.qualificador.model.ExperienciaProfissional;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/experiencias")
public class ExperienciaProfissionalController {

    @Autowired
    private ExperienciaProfissionalService experienciaProfissionalService;
    private Optional<Usuario> usuarioSistema;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrarExProfissionais")
    public ModelAndView carregarExProfissionais(@AuthenticationPrincipal User user, ExperienciaProfissional experienciaProfissional) {
        ModelAndView mv = new ModelAndView("exProfissionais/exProfissionais");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        return mv;
    }

    @PostMapping("/cadastrarExProfissionais")
    public ModelAndView cadastrarExProfissionais(@Valid @ModelAttribute ExperienciaProfissional experienciaProfissional,
            BindingResult result, RedirectAttributes attribute, @AuthenticationPrincipal User user, Usuario usuario) {
        if (result.hasErrors()) {
            return carregarExProfissionais(user, experienciaProfissional);
        }
        usuario = usuarioSistema.get();
        experienciaProfissional.setUsuario(usuario);
        experienciaProfissionalService.addNew(experienciaProfissional);

        attribute.addFlashAttribute("success", "Habilidades adicionadas com sucesso!");
        return new ModelAndView("redirect:/experiencias/cadastrarExProfissionais");

    }

    @GetMapping("/listarExperienciasProfissionais")
    public ModelAndView listarExProfissionais(@AuthenticationPrincipal User user, ExperienciaProfissional experienciaProfissional) {
        ModelAndView mv = new ModelAndView("exProfissionais/listaDeExpProfissionais");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        mv.addObject("listaDeExperieciasProfissionais", experienciaProfissionalService.findByUsuario(usuarioSistema.get()));

        return mv;
    }

    @GetMapping("/editarExperienciasProfissionais/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("exProfissionais/exProfissionais");
        Optional<ExperienciaProfissional> experienciaProfOptional = experienciaProfissionalService.getOne(codigo);
        mv.addObject("experienciaProfissional", experienciaProfOptional.get());
        return mv;

    }

    @GetMapping("/eliminarExProfissionais/{codigo}")
    public ModelAndView deleteFormacaoAcademica(@PathVariable("codigo") Long codigo) {
        experienciaProfissionalService.delete(codigo);
        return new ModelAndView("redirect:/experiencias/listarExperienciasProfissionais");

    }

}
