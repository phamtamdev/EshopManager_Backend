package com.tambao.EshopManeger_Backend.service.Impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String from, String to, String subject, String body) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body, true);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
