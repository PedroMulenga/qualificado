/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;

import com.qualificador.ExceptionMessages.NomeExistenteException;
import com.qualificador.Service.CertificacaoService;
import com.qualificador.Service.UsuarioService;
import com.qualificador.model.Certificacao;
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
@RequestMapping("/certificacoes")
public class CertificacaoController {

    @Autowired
    private CertificacaoService certificacaoService;
    private Optional<Usuario> usuarioSistema;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrarCertificacao")
    public ModelAndView carregarCertificacoes(@AuthenticationPrincipal User user, Certificacao certificacao) {
        ModelAndView mv = new ModelAndView("certificacoes/certificacoes");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        return mv;
    }

    @PostMapping("/cadastrarCertificacao")
    public ModelAndView cadastrarCertificacao(@Valid @ModelAttribute Certificacao certificacao,
            BindingResult result, RedirectAttributes attribute, @AuthenticationPrincipal User user, Usuario usuario) {
        if (result.hasErrors()) {
            return carregarCertificacoes(user, certificacao);
        }
        usuario = usuarioSistema.get();
        certificacao.setUsuario(usuario);
        certificacaoService.addNew(certificacao);

        attribute.addFlashAttribute("success", "Certificação adicionadas com sucesso!");
        return new ModelAndView("redirect:/certificacoes/cadastrarCertificacao");

    }

    @GetMapping("/listarCertificacoes")
    public ModelAndView listarCertificacoes(@AuthenticationPrincipal User user, Certificacao certificacao) {
        ModelAndView mv = new ModelAndView("certificacoes/listaDeCertificacoes");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        mv.addObject("listaDeCertificacoes", certificacaoService.findByUsuario(usuarioSistema.get()));

        return mv;
    }

    @GetMapping("/editarCertificacao/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("certificacoes/certificacoes");
        Optional<Certificacao> certificacaoOptional = certificacaoService.getOne(codigo);
        mv.addObject("certificacao", certificacaoOptional.get());
        return mv;

    }


    @GetMapping("/eliminarCertificacao/{codigo}")
    public ModelAndView deleteFormacaoAcademica(@PathVariable("codigo") Long codigo) {
        certificacaoService.delete(codigo);
        return new ModelAndView("redirect:/certificacoes/listarCertificacoes");

    }

}
