package com.Vishal.Sharma.APIRateLimiter.adviceTests;

import com.Vishal.Sharma.APIRateLimiter.service.LimitService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResourceNotFoundExceptionTest {

    @Autowired
    LimitService limitService;

    @Test
    @Disabled
    void testingResourceNotFoundException() {
        limitService.checkTillApiLimit("vishal11");
        int a = 5;
    }
}
