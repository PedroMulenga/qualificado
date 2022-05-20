/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.model.Permissao;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping("/cadastrarPermissao")
    public ModelAndView novaPermissao(Permissao permissao) {
        ModelAndView mv = new ModelAndView("security/permissao");
        return mv;
    }

    @GetMapping("/listarPermissoes")
    public ModelAndView listarPermissoes(Permissao permissao) {
        ModelAndView mv = new ModelAndView("security/permissao");
        mv.addObject("listaPermissoes", permissaoService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarPermissao")
    public ModelAndView cadastrarPermissao(@Valid Permissao permissao, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novaPermissao(permissao);
        }
        try {
            permissaoService.addNew(permissao);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return novaPermissao(permissao);
        }
        attribute.addFlashAttribute("success", "Permissão salva com sucesso!");
        return new ModelAndView("redirect:/listarPermissoes");

    }

    @RequestMapping("/getOnePermissao")
    @ResponseBody
    public Optional<Permissao> getOne(Long codigo) {
        return permissaoService.getOne(codigo);
    }

    @RequestMapping(value = "/editarPermissao", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Permissao permissao) {
        permissaoService.update(permissao);
        return "redirect:/listarPermissoes";

    }

    @RequestMapping(value = "/eliminarPermissao", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        permissaoService.delete(codigo);
        return "redirect:/listarPermissoes";

    }

}
