package com.mangement.restaurant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority
    .SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.security.model.User;
import com.mangement.restaurant.security.repository.UserRepository;

import java.util.List;

@Service
public class CustomUserDetailsService 
        implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Find user by email
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> 
                new UsernameNotFoundException(
                    "User not found: " + email));

        // Check if account is active
        if (!user.isActive()) {
            throw new UsernameNotFoundException(
                "Account is disabled: " + email);
        }

        // Return Spring's UserDetails with role as authority
        return new org.springframework.security.core
                    .userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority(
                user.getRole().name()))  // e.g. "ROLE_ADMIN"
        );
    }
}
