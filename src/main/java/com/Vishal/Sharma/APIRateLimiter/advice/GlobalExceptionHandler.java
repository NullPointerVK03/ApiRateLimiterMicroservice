package com.Vishal.Sharma.APIRateLimiter.advice;

import com.Vishal.Sharma.APIRateLimiter.exception.AccessLimitExceededException;
import com.Vishal.Sharma.APIRateLimiter.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handlingResourcesNotFoundException(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessLimitExceededException.class)
    public ResponseEntity<ApiError> handlingApiAccessLimitExceededException(AccessLimitExceededException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handlingUsernameNotFoundException(UsernameNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handlingAuthenticationException(AuthenticationException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getLocalizedMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handlingJwtException(JwtException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getLocalizedMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }



}
