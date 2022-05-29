/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualificador.Controllers;
import com.qualificador.ExceptionMessages.EmailUsuarioExistenteException;
import com.qualificador.Service.UsuarioService;
import com.qualificador.Utility;
import com.qualificador.model.Usuario;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class ForgotPasswordController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgotPassword")
    public ModelAndView showForgotPassword() {
        ModelAndView mv = new ModelAndView("forgotPassWord");
        return mv;
    }

    @PostMapping("/forgotPassword")
    public ModelAndView formForgotPassword(HttpServletRequest request, RedirectAttributes attribute) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);
        try {
            usuarioService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSystemURL(request) + "/resetPassword?token=" + token;
            sendEmail(email, resetPasswordLink);
            attribute.addFlashAttribute("success", "Enviamos um email de confirmação! Verifique sua caixa de entrada.");
        } catch (EmailUsuarioExistenteException e) {
            attribute.addFlashAttribute("error", e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            attribute.addFlashAttribute("error", "Erro ao enviar o E-mail");
        }
        return new ModelAndView("redirect:/forgotPassword");
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("exemplo@gmail.com", "Qualificado");
        helper.setTo(email);
        String subject = "Aqui está o link para alterares a sua senha";
        String content = "<p>Olá!</p>"
                + "<p>Você tem permissão para alterares a sua senha</p>"
                + "<p>Click no ink abaixo para mudares a sua senha</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Mudar minha senha</a></b></p>"
                + "<p>Ignore este email se tu lembrares a senha ou se não quiseres recupera-la</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

    }

    @GetMapping("/resetPassword")
    public ModelAndView resetPasswordForm(@Param("token") String token, RedirectAttributes attribute) {
        Usuario usuario = usuarioService.get(token);
        ModelAndView mv = new ModelAndView("resetNewPassWord");
        if (usuario == null) {
            attribute.addFlashAttribute("error", "Token inválido");
            return new ModelAndView("message");
        }
        mv.addObject("token", token);

        return mv;
    }

    @PostMapping("/resetPassword")
    public ModelAndView alterarSenha(HttpServletRequest request, RedirectAttributes attribute) {
        String token = request.getParameter("token");
        String password = request.getParameter("senha");
        Usuario usuario = usuarioService.get(token);
        if (usuario == null) {
            attribute.addFlashAttribute("error", "Token inválido");
            return new ModelAndView("message");
        }else{
            usuarioService.updatePassword(usuario, password);
            attribute.addFlashAttribute("success", "Senha alterada com sucesso");
        }
        return new ModelAndView("redirect:/login");
    }
}
