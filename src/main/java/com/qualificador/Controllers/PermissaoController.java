/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;
import com.qualificador.ExceptionMessages.NomeExistenteException;
import com.qualificador.Service.PermissaoService;
import com.qualificador.model.Permissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/usuarios")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping("/cadastrarPermissao")
    public ModelAndView novaPermissao(Permissao permissao) {
        ModelAndView mv = new ModelAndView("security/permissao");
        return mv;
    }

    @RequestMapping("/listarPermissoes")
    public ModelAndView listarPermissoes() {

        return listarPermissoes(1);
    }

    @GetMapping("/listarPermissoes/{pageNumber}")
    public ModelAndView listarPermissoes(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("security/listaPermissoes");
        Page<Permissao> page = permissaoService.findAll(paginaCorrente);
        List<Permissao> listaDePermissoes = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaPermissoes", listaDePermissoes);

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
        attribute.addFlashAttribute("success", "Permiss??o salva com sucesso!");
        return new ModelAndView("redirect:/usuarios/listarPermissoes");

    }

    @GetMapping("/editarPermissao/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/permissao");
        Optional<Permissao> apermissaoOptional = permissaoService.getOne(codigo);
        mv.addObject("permissao", apermissaoOptional);
        return mv;

    }

    @GetMapping(value = "/eliminarPermissao/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        permissaoService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

}
