package com.example.buoi9.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    private final static Logger log = LoggerFactory.getLogger(EmailService.class);

    @Override
    public String sentEmail(String to, String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
        messageHelper.setSubject("Love you 3000");
        messageHelper.setFrom("tienkiennropro@gmail.com");
        messageHelper.setText(email, true);
        messageHelper.setTo(to);
        javaMailSender.send(mimeMessage);
        return "Sent successfully!";
    }
}
