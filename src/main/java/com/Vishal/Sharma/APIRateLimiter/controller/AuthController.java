package com.Vishal.Sharma.APIRateLimiter.controller;

import com.Vishal.Sharma.APIRateLimiter.JwtUtils.JwtService;
import com.Vishal.Sharma.APIRateLimiter.dto.AuthResponseDto;
import com.Vishal.Sharma.APIRateLimiter.dto.UserDTO;
import com.Vishal.Sharma.APIRateLimiter.repository.UserRepository;
import com.Vishal.Sharma.APIRateLimiter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> userRegistration(@RequestBody UserDTO user, HttpServletResponse response) {

        AuthResponseDto authResponseDto = userService.userRegistration(user, response);
        return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponseDto> userLogin(@RequestBody UserDTO userDTO, HttpServletResponse response) {

        AuthResponseDto authResponseDto = userService.userLogin(userDTO, response);
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(HttpServletRequest request) {

        AuthResponseDto authResponseDto = userService.refresh(request);
        return ResponseEntity.ok(authResponseDto);
    }
}
