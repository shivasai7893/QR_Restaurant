package com.mangement.restaurant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ── 1. Read Authorization header ─────────────────────
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // Header must be: "Bearer eyJhbGci..."
        if (authHeader != null 
                && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // remove "Bearer "
            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                // Invalid token — just continue, 
                // security config will block it
            }
        }

        // ── 2. Validate token and set authentication ──────────
        if (email != null 
                && SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

            UserDetails userDetails = 
                userDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()  // roles
                    );

                authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                // ✅ User is authenticated — set in context
                SecurityContextHolder.getContext()
                    .setAuthentication(authToken);
            }
        }

        // ── 3. Continue to next filter ────────────────────────
        filterChain.doFilter(request, response);
    }
}