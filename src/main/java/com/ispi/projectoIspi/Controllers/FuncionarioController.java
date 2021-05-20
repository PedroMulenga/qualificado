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
import com.ispi.projectoIspi.Service.FuncionarioService;
import com.ispi.projectoIspi.Service.MunicipioService;
import com.ispi.projectoIspi.Service.ProvinciaService;
import com.ispi.projectoIspi.model.Funcionario;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private BairroRepository bairroRepository;

    @GetMapping("/cadastrarFuncionarios")
    public ModelAndView novoFuncionario(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("funcionarios/funcionario");
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("municipios", municipioService.getAll());
        mv.addObject("bairros", bairroRepository.findAll());
        return mv;
    }

    @PostMapping("/cadastrarFuncionarios")
    public ModelAndView cadastroFuncionarios(@Valid @ModelAttribute Funcionario funcionario, BindingResult result, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("redirect:/cadastrarFuncionarios");
        if (result.hasErrors()) {
            return novoFuncionario(funcionario);
        }
        try {
            funcionarioService.addNew(funcionario);
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return mv;
    }

    @GetMapping("/listarFuncionarios")
    public ModelAndView listarFuncionarios(Funcionario funcionario, @PageableDefault(size = 5) Pageable pageable) {
        ModelAndView mv = new ModelAndView("funcionarios/listaFuncionarios");
        Page<Funcionario> page = funcionarioService.findAll(pageable);
        List<Funcionario> listaDeFuncionarios = page.getContent();
        long totalRegistoPorPaginas = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("totalRegistoPorPaginas", totalRegistoPorPaginas);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaFuncionarios", listaDeFuncionarios);
        //mv.addObject("provincias", provinciaService.findAll());
        //mv.addObject("municipios", municipioService.getAll());
        //mv.addObject("bairros", bairroRepository.findAll());
        return mv;
    }

    @RequestMapping("/getOneFuncionario")
    @ResponseBody
    public Optional<Funcionario> getOne(Long codigo) {
        return funcionarioService.getOne(codigo);
    }

    @RequestMapping(value = "/editarFuncionario", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Funcionario funcionario) {
        funcionarioService.update(funcionario);
        return "redirect:/listarFuncionarios";

    }

    @RequestMapping(value = "/eliminarFuncionario", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        funcionarioService.delete(codigo);
        return "redirect:/listarFuncionarios";

    }

}
