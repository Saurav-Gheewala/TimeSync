package com.example.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    public ResponseEntity<String> sendMail(String email, byte[] certificate) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setTo(email);
        mailMessage.setSubject("Your Timetable");
        mailMessage.setText("Your Timetable Is Ready...");
        mailMessage.addAttachment("Timetable.pdf", new ByteArrayResource(certificate));
        javaMailSender.send(message);
        System.out.println("Sent");
        return new ResponseEntity<>("Semd", HttpStatus.CREATED);
    }

}
