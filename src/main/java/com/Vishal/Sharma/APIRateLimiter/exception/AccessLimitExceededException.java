package com.Vishal.Sharma.APIRateLimiter.exception;

public class AccessLimitExceededException extends RuntimeException{
    public AccessLimitExceededException(String message) {
        super(message);
    }
}

