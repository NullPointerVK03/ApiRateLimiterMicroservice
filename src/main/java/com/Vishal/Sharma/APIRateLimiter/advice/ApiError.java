package com.Vishal.Sharma.APIRateLimiter.advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Builder
public class ApiError {

    private String message;
    private HttpStatus status;
    private Date timestamp;


}
