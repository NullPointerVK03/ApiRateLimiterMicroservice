package com.Vishal.Sharma.APIRateLimiter.service;

import com.Vishal.Sharma.APIRateLimiter.entity.User;
import com.Vishal.Sharma.APIRateLimiter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDb = userRepository.findByUserName(username);
        if (userInDb == null)
            throw new BadCredentialsException("User with userName: " + username + " not found");

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(userInDb.getUsername())
                .password(userInDb.getPassword())
                .authorities(userInDb.getAuthorities())
                .build();
    }
}
