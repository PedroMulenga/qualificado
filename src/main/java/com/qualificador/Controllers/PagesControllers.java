/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/paginas")
public class PagesControllers {

    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView mv = new ModelAndView("/about");
        return mv;
    }
    @GetMapping("/testimonials")
    public ModelAndView testimonials() {
        ModelAndView mv = new ModelAndView("/testimonials");
        return mv;
    }
    @GetMapping("/services")
    public ModelAndView services() {
        ModelAndView mv = new ModelAndView("/services");
        return mv;
    }

}
