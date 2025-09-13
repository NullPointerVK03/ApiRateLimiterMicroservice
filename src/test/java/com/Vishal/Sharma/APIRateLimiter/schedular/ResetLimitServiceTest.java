package com.Vishal.Sharma.APIRateLimiter.schedular;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResetLimitServiceTest {

    @Autowired
    private ResetLimitService resetLimitService;


    @Test
    @Disabled
    void resetLimitCheck(){
        resetLimitService.resetAccessLimit();
    }
}
