package com.Vishal.Sharma.APIRateLimiter.controller;

import com.Vishal.Sharma.APIRateLimiter.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final LimitService limitService;


    //    API limit checker end-point
    @GetMapping("/api-limiter-check")
    public ResponseEntity<String> apiLimitCheckMethod() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        limitService.checkTillApiLimit(userName);
        return new ResponseEntity<>("Access granted", HttpStatus.OK);
    }

    //    dataBaseAccess limit checker end-point
    @GetMapping("/data-base-limiter-check")
    public ResponseEntity<String> dataBaseLimitCheckMethod() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        limitService.checkTillDataBaseLimit(userName);
        return new ResponseEntity<>("Database access granted", HttpStatus.OK);
    }

    //    anonymousAccess limit checker end-point
    @GetMapping("/anonymous-access-limiter-check")
    public ResponseEntity<String> anonymousAccessLimitCheckMethod() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        limitService.checkTillAnonymousAccessLimit(userName);
        return new ResponseEntity<>("Anonymous access granted", HttpStatus.OK);
    }

}
