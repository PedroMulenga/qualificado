/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.Enum.TipoEmolumento;
import com.ispi.projectoIspi.Service.EmolumentoService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.model.Emolumento;
import com.ispi.projectoIspi.model.Matricula;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class VerificarPagamentoTransporteController {

    @Autowired
    private EmolumentoService emolumentoService;
    @Autowired
    private MatriculaService matriculaService;

    Matricula matricula;
    Iterable<Emolumento> emolumentos;

    @RequestMapping(value = "/listarPagamentoTransporte", method = RequestMethod.GET)
    public ModelAndView carregarPagina(TipoEmolumento tipoEmolumento) {
        ModelAndView mv = new ModelAndView("servicos/controlTransporte");
        return mv;
    }

    @PostMapping(value = "/pagamentoTransporte/{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Matricula findByNumeroEstudante(@PathVariable("numeroEstudante") String numeroEstudante, SituacaoMatricula situacao, TipoEmolumento tipoEmolumento) {
        matricula = matriculaService.findByNumeroEstudanteAndSituacao(numeroEstudante, situacao.MATRICULADO);
        if (matricula != null) {
            emolumentos = emolumentoService.findByMatriculaAndTipoEmolumento(matricula, tipoEmolumento.TRANSPORTE);
        } else {
            emolumentos = null;
        }
        return matricula;
    }

    @RequestMapping(value = "/carregarPaginaFragmento", method = RequestMethod.GET)
    public ModelAndView carregarPaginaFragmento() {
        ModelAndView mv = new ModelAndView("servicos/fragmentTable");
        mv.addObject("emolumentos", emolumentos);
        return mv;
    }

    @RequestMapping(value = "/verificacaoRapida", method = RequestMethod.GET)
    public ModelAndView carregarPaginaVerificacaoRapida() {
        ModelAndView mv = new ModelAndView("servicos/controlTransporteRapido");
        return mv;
    }

    @PostMapping(value = "/verificacaoRapida/{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Emolumento findByMesReferente(@PathVariable("numeroEstudante") String numeroEstudante) {
        Emolumento emolumento = emolumentoService.findByNumeroEstudanteAndMesReferente(numeroEstudante);
        return emolumento;
    }

}
