package com.Vishal.Sharma.APIRateLimiter.JwtUtils;

import com.Vishal.Sharma.APIRateLimiter.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.token.allowed.time.period}")
    private Long exp;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    //    creation of JWT token for a User
    public String generateToken(User user) {

        Map<String, Object> defaultClaims = new HashMap<>();
        defaultClaims.put("name", user.getUsername());
        defaultClaims.put("iat", System.currentTimeMillis());
        defaultClaims.put("exp", System.currentTimeMillis() + exp);
        defaultClaims.put("roles", user.getRoles());


        return Jwts.builder()
                .subject(user.getUsername())
                .claims(defaultClaims)
                .signWith(getKey())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();

    }
}
