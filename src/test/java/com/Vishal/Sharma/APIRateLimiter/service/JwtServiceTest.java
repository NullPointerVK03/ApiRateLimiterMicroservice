package com.Vishal.Sharma.APIRateLimiter.service;

import com.Vishal.Sharma.APIRateLimiter.JwtUtils.JwtService;
import com.Vishal.Sharma.APIRateLimiter.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @Disabled
    void tokenGenerationByUserTest() {
        User dummyUser = User.builder().userName("dum3").password("123").build();
        String token = jwtService.generateRefreshToken(dummyUser);
        int a = 5;
    }

    @Test
    @Disabled
    void extractUsernameFromJwtTokenTest() {
        String userName = jwtService.getUserNameFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW0zIiwibmFtZSI6ImR1bTMiLCJleHAiOjE3NTcxNjc3Mzg3NDEsImlhdCI6MTc1NzE2NzQzODc0MX0.8S9CMm4MeDlgui2VA9YH1QFjnI0kcFsKg8FYl8FazLM");
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        int a = 5;
    }
}
