package br.com.techlead.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;


    public void enviaEmailComSenhaProvisoria(String email, String novaSenha) {
        String subject = "Sua senha provisória";
        String body = "Sua senha provisória é: " + novaSenha;
        this.sendEmail(email, subject, body);
    }

    private void sendEmail(String email, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom("bibliotech@gmail.com");
        javaMailSender.send(mailMessage);
    }
}
