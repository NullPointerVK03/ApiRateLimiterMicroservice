package com.Vishal.Sharma.APIRateLimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
public class ApiRateLimiterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRateLimiterApplication.class, args);
    }

}
