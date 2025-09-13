package com.Vishal.Sharma.APIRateLimiter.controller;

import com.Vishal.Sharma.APIRateLimiter.JwtUtils.JwtService;
import com.Vishal.Sharma.APIRateLimiter.dto.UserDTO;
import com.Vishal.Sharma.APIRateLimiter.entity.User;
import com.Vishal.Sharma.APIRateLimiter.repository.UserRepository;
import com.Vishal.Sharma.APIRateLimiter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<String> userRegistration(@RequestBody UserDTO user) {
        try {
            String jwtToken = userService.createNewUser(user);
            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUsingJwt(@RequestBody UserDTO userDTO) {
        User userInDb = userRepository.findByUserName(userDTO.getUserName());

        if (userInDb == null)
            throw new BadCredentialsException("Invalid username or password, try with different credentials");


        String token = jwtService.generateToken(userInDb);
        return ResponseEntity.ok(token);


    }
}
