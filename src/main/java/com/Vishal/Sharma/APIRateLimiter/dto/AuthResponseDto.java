package com.Vishal.Sharma.APIRateLimiter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String userName;
    private String accessToken;
    private String refreshToken;
}
