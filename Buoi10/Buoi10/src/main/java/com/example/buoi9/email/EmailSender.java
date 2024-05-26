package com.example.buoi9.email;

import javax.mail.MessagingException;

public interface EmailSender {
    String sentEmail(String to, String email) throws MessagingException;
}
