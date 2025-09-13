package com.Vishal.Sharma.APIRateLimiter.dto;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NonNull
    @Indexed(unique = true)
    public String userName;

    @NonNull
    public String password;
}
