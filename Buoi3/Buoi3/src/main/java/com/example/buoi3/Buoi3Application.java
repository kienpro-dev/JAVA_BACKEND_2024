package com.example.buoi3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Buoi3Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Buoi3Application.class, args);
        Phone phone = context.getBean(Phone.class);
        System.out.println(phone.getName());
    }

}
