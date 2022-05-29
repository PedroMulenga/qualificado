/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;

import com.qualificador.Service.SendEmailService;
import com.qualificador.helper.Contacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/contactos")
public class EmailController {

    @Autowired
    SendEmailService emailService;

    @GetMapping("/sendEmail")
    public ModelAndView sendEmail(Contacto contacto) {
        ModelAndView mv = new ModelAndView("contact");
        return mv;

    }

    @PostMapping("/sendEmail")
    public ModelAndView enviar(Contacto contacto) {
        emailService.sendEMail(contacto);
        System.out.println("Enviado»»»»");
        return new ModelAndView("redirect:/criarContaUsuarios");
    }
}
