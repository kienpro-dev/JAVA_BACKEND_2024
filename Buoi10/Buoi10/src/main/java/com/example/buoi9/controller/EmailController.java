package com.example.buoi9.controller;

import com.example.buoi9.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestParam String to, @RequestParam String message) throws MessagingException {
        return ResponseEntity.ok().body(emailService.sentEmail(to, message));
    }
}
