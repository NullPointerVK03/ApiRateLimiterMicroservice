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

    @Value("${token.exp.accessToken}")
    private String accessTokenExp;

    @Value("${token.exp.refreshToken}")
    private String refreshTokenExp;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> defaultClaims = new HashMap<>();
        defaultClaims.put("name", user.getUsername());
        defaultClaims.put("iat", System.currentTimeMillis());
        defaultClaims.put("roles", user.getRoles());
        return defaultClaims;
    }

    //    creation of JWT token for a User
    public String generateAccessToken(User user) {

        Map<String, Object> claims = getClaims(user);
        claims.put("exp", System.currentTimeMillis() + Long.parseLong(accessTokenExp));
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(User user) {

        Map<String, Object> claims = getClaims(user);
        claims.put("exp", System.currentTimeMillis() + Long.parseLong(refreshTokenExp));
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .signWith(getKey())
                .compact();
    }


    public String getUserNameFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();

    }
}
