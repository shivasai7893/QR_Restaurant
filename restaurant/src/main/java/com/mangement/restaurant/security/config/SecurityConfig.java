package com.mangement.restaurant.security.config;

import com.mangement.restaurant.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation
    .authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation
    .method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation
    .web.builders.HttpSecurity;
import org.springframework.security.config.annotation
    .web.configuration.EnableWebSecurity;
import org.springframework.security.config.http
    .SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt
    .BCryptPasswordEncoder;
import org.springframework.security.crypto.password
    .PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication
    .UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // enables @PreAuthorize on controllers
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) 
            throws Exception {

        http
            // Disable CSRF — we use JWT (stateless)
            .csrf(csrf -> csrf.disable())

            // No sessions — every request must carry token
            .sessionManagement(session -> session
                .sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))

            // ── URL Permission Rules ──────────────────────────
            .authorizeHttpRequests(auth -> auth

                // ✅ Public — anyone can hit these
                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/api/menu-items",
                    "/api/menu-items/**",
                    "/api/categories",
                    "/api/categories/**"
                ).permitAll()

                // 🔴 Admin only
                .requestMatchers(
                    "/api/admin/**"
                ).hasAuthority("ROLE_ADMIN")

                // 🟡 Admin + Waiter
                .requestMatchers(
                    "/api/orders",
                    "/api/orders/**"
                ).hasAnyAuthority(
                    "ROLE_ADMIN", 
                    "ROLE_WAITER",
                    "ROLE_KITCHEN")

                // 🟢 Customer
                .requestMatchers(
                    "/api/customer/**"
                ).hasAuthority("ROLE_CUSTOMER")

                // 🟠 Kitchen
                .requestMatchers(
                    "/api/kitchen/**"
                ).hasAnyAuthority(
                    "ROLE_KITCHEN", 
                    "ROLE_ADMIN")

                // Everything else needs authentication
                .anyRequest().authenticated()
            )

            // Add JWT filter BEFORE Spring's default auth filter
            .addFilterBefore(
                jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt password encoder — always use this, never plain text
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}