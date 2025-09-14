package com.Vishal.Sharma.APIRateLimiter.service;

import com.Vishal.Sharma.APIRateLimiter.JwtUtils.JwtService;
import com.Vishal.Sharma.APIRateLimiter.dto.AuthResponseDto;
import com.Vishal.Sharma.APIRateLimiter.dto.UserDTO;
import com.Vishal.Sharma.APIRateLimiter.entity.Limit;
import com.Vishal.Sharma.APIRateLimiter.entity.User;
import com.Vishal.Sharma.APIRateLimiter.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LimitService limitService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponseDto userRegistration(UserDTO user, HttpServletResponse response) {
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

        String accessToken = jwtService.generateAccessToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new AuthResponseDto(user.getUserName(), accessToken, refreshToken);
    }

    public AuthResponseDto userLogin(UserDTO userDTO, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.getUserName(), userDTO.getPassword()
        ));

        User userInDb = userRepository.findByUserName(userDTO.getUserName());

        String accessToken = jwtService.generateAccessToken(userInDb);
        String refreshToken = jwtService.generateRefreshToken(userInDb);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new AuthResponseDto(userDTO.getUserName(), accessToken, refreshToken);

    }

    public AuthResponseDto refresh(HttpServletRequest request) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new JwtException("Your refresh token is not valid, please login again"));

        String userName = jwtService.getUserNameFromToken(refreshToken);
        User userInDb = userRepository.findByUserName(userName);
        String accessToken = jwtService.generateAccessToken(userInDb);

        return new AuthResponseDto(userName, accessToken, refreshToken);
    }
}
