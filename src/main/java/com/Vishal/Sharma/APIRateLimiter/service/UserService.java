package com.Vishal.Sharma.APIRateLimiter.service;

import com.Vishal.Sharma.APIRateLimiter.JwtUtils.JwtService;
import com.Vishal.Sharma.APIRateLimiter.dto.UserDTO;
import com.Vishal.Sharma.APIRateLimiter.entity.Limit;
import com.Vishal.Sharma.APIRateLimiter.entity.User;
import com.Vishal.Sharma.APIRateLimiter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LimitService limitService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;


    public String createNewUser(UserDTO user) {
        try {
//            conversion of UserDto to User
            User newUser = User.builder()
                    .userName(user.getUserName())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(List.of("USER"))
                    .build();

//            creating a new Limit for current user
            Limit limit = limitService.createNewLimit();
            newUser.setLimit(limit);

//            saving the new User in database
            userRepository.save(newUser);

//            generation of jwtToken for current user
            return jwtService.generateToken(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
