package com.mangement.restaurant.security.controller;

import com.mangement.restaurant.dto.*;
import com.mangement.restaurant.security.*;
import com.mangement.restaurant.security.dto.AuthResponse;
import com.mangement.restaurant.security.dto.LoginRequest;
import com.mangement.restaurant.security.dto.RegisterRequest;
import com.mangement.restaurant.security.model.User;
import com.mangement.restaurant.security.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ── REGISTER ─────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body("Email already registered!");
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // ✅ Always encode password — never save plain text
        user.setPassword(
            passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());
        user.setActive(true);

        userRepository.save(user);

        return ResponseEntity
            .status(201)
            .body("User registered successfully!");
    }

    // ── LOGIN ─────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try {
            // ✅ This checks email + password automatically
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(401)
                .body("Invalid email or password!");
        }

        // Load user and generate token
        UserDetails userDetails = 
            userDetailsService.loadUserByUsername(
                request.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        // Get role and name from DB for response
        User user = userRepository
            .findByEmail(request.getEmail()).get();

        return ResponseEntity.ok(
            new AuthResponse(
                token,
                user.getRole().name(),
                user.getName(),
                user.getEmail()
            )
        );
    }

    // ── GET PROFILE ───────────────────────────────────────────

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        User user    = userRepository
            .findByEmail(email).orElseThrow();

        return ResponseEntity.ok(
            new AuthResponse(
                null,
                user.getRole().name(),
                user.getName(),
                user.getEmail()
            )
        );
    }
}
