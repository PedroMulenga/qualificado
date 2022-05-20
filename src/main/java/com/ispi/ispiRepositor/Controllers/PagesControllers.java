/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class PagesControllers {

    @GetMapping("/servicePage")
    public ModelAndView sirvicePage() {
        ModelAndView mv = new ModelAndView("servicos/service");
        return mv;
    }

    @GetMapping("/other")
    public ModelAndView otherPage() {
        ModelAndView mv = new ModelAndView("outros/other");
        return mv;
    }
    @GetMapping("/secretariaPage")
    public ModelAndView secretariaPage() {
        ModelAndView mv = new ModelAndView("secretaria/secretaria");
        return mv;
    }
    @GetMapping("/transportePage")
    public ModelAndView transportePage() {
        ModelAndView mv = new ModelAndView("servicos/transportes");
        return mv;
    }
    @GetMapping("/myPayments")
    public ModelAndView myPayments() {
        ModelAndView mv = new ModelAndView("servicos/meusPagamentos");
        return mv;
    }

}
