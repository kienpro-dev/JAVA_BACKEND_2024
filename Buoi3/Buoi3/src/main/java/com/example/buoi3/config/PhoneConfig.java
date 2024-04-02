package com.example.buoi3.config;

import com.example.buoi3.Phone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhoneConfig {

    @Bean
    public Phone get() {
        return new Phone("Samsung");
    }
}
