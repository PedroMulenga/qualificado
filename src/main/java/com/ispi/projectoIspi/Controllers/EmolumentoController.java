/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.Service.EmolumentoService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.model.Emolumento;
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
public class EmolumentoController {

    @Autowired
    private EmolumentoService emolumentoService;
    @Autowired
    private MatriculaService matriculaService;

    Matricula matricula;

    @GetMapping("/pagamentoAluno")
    public ModelAndView novoPagamento() {
        ModelAndView mv = new ModelAndView("servicos/emolumento");
        mv.addObject("emolumento", new Emolumento());
        mv.addObject("emolumentos", emolumentoService.getAll());
        return mv;
    }

    @PostMapping(value = "/pegarMatricula/{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Matricula findByNumeroEstudante(@PathVariable("numeroEstudante") String numeroEstudante, SituacaoMatricula situacao) {
        matricula = matriculaService.findByNumeroEstudanteAndSituacao(numeroEstudante, situacao.MATRICULADO);
        return matricula;
    }

    @PostMapping("/pagamentoAluno")
    public ModelAndView salvarPagamento(@Valid @ModelAttribute Emolumento emolumento,
            BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("servicos/emolumento");
            mv.addObject("matricula", matricula);
            return mv;
        } else {
            emolumento.setMatricula(matricula);
            emolumentoService.addNew(emolumento);
            attribute.addFlashAttribute("success", "Pagamento efectuado com sucesso!");
            return new ModelAndView("redirect:pagamentoAluno");
        }
    }

    @GetMapping("/listarPagamentosAlunos")
    public ModelAndView listarPagamentos(Emolumento emolumento) {
        ModelAndView mv = new ModelAndView("servicos/emolumento");
        mv.addObject("listaPagamentosAlunos", emolumentoService.getAll());
        mv.addObject("listaMatriculaAlunos", matriculaService.getAll());
        return mv;
    }

    @RequestMapping("/getOnePagamentoAluno")
    @ResponseBody
    public Optional<Emolumento> getOne(Long codigo) {
        return emolumentoService.getOne(codigo);
    }

    @RequestMapping(value = "/editarPagamentoAluno", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Emolumento emolumento, RedirectAttributes attribute) {
        emolumentoService.update(emolumento);
        attribute.addFlashAttribute("success", "Pagamento actualizado com sucesso!");
        return "redirect:/listarPagamentosAlunos";

    }

    @RequestMapping(value = "/eliminarPagamentoAluno", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo, RedirectAttributes attribute) {
        emolumentoService.delete(codigo);
        attribute.addFlashAttribute("warning", "Registo eliminado com sucesso!");
        return "redirect:/listarPagamentosAlunos";

    }

}
