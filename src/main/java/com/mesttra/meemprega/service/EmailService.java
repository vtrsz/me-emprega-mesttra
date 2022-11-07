package com.mesttra.meemprega.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String sendEmail(String keyWords, String email, String jobs) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo(email);
            helper.setSubject( "Vagas de " + keyWords);
            helper.setText(jobs, true);
            mailSender.send(mail);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar e-mail";
        }
    }
}
