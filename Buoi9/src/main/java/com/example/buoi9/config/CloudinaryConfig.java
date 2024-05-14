package com.example.buoi9.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    Cloudinary configCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("api_key", "672113941214312");
        config.put("api_secret", "UpAWhIogsMKDFJNMKyxq2xvzTgs");
        config.put("cloud_name", "shopmanagement");
        return new Cloudinary(config);
    }
}
