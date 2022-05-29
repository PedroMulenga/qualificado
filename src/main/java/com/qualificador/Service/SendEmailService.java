/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Service;

import com.qualificador.helper.Contacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEMail(Contacto contacto) {
        System.out.println("««««Enviando E-mail»»»");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("pedromulenga21@gmail.com");
        simpleMailMessage.setTo(contacto.getTo());
        simpleMailMessage.setSubject(contacto.getTopic());
        simpleMailMessage.setText(contacto.getBody());
        javaMailSender.send(simpleMailMessage);
        return "Enviado";

    }

}
