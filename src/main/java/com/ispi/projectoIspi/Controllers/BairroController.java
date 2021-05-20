/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Repository.BairroRepository;
import com.ispi.projectoIspi.Service.MunicipioService;
import com.ispi.projectoIspi.model.Bairro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class BairroController {

    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private BairroRepository bairroRepository;

    @GetMapping("/cadastrarBairro")
    public ModelAndView novoRegisto(Bairro bairro) {
        ModelAndView mv = new ModelAndView("outros/bairro");
        mv.addObject("bairro", new Bairro());        
        return mv;
    }

    @GetMapping("/listarBairros")
    public ModelAndView listarBairro(Bairro bairro) {
        ModelAndView mv = new ModelAndView("outros/bairro");
        mv.addObject("listaBairro", bairroRepository.findAll());
        mv.addObject("municipios", municipioService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarBairro")
    public String cadastrarBairro(Bairro bairro) {
        bairroRepository.save(bairro);
        return "redirect:/listarBairros";

    }

    @RequestMapping("/getOneBairro")
    @ResponseBody
    public Optional<Bairro> getOne(Long codigo) {
        return bairroRepository.findById(codigo);
    }

    @RequestMapping(value = "/editarBairro", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Bairro bairro) {
        bairroRepository.save(bairro);
        return "redirect:/listarBairros";

    }

    @RequestMapping(value = "/eliminarBairro", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        bairroRepository.deleteById(codigo);
        return "redirect:/listarBairros";

    }

}
