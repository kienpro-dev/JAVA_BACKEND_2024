package com.example.buoi9.schedule;

import com.example.buoi9.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class ScheduleMail {
    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 1)
    void sentMailSchedule() throws MessagingException {
        emailService.sentEmail("hoangphuong270703@gmail.com", "Hello");
    }
}
