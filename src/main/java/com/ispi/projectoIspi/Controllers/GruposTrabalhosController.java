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
public class GruposTrabalhosController {

    @GetMapping("/gruposTrabalho")
    public ModelAndView indexTeam() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("team/indexTeam");
        return mv;
    }

}
