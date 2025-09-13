package com.Vishal.Sharma.APIRateLimiter.service;

import com.Vishal.Sharma.APIRateLimiter.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @Disabled
    void loadUserByUsernameTest() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("dum2");
        int a = 5;
    }
}
