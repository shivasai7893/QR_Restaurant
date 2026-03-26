package com.mangement.restaurant.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ⚠️ Move this to application.properties in production
    // jwt.secret=your-super-secret-key-minimum-32-characters
    private final String SECRET_KEY = 
        "restaurant-secret-key-minimum-256-bits-long!!";

    private final long EXPIRATION_MS = 86400000; // 24 hours

    // Build the signing key from secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ── Generate token ────────────────────────────────────────

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())   // email
            .claim("roles", userDetails
                .getAuthorities()
                .toString())                          // role
            .setIssuedAt(new Date())
            .setExpiration(
                new Date(System.currentTimeMillis() + EXPIRATION_MS))
            .signWith(getSigningKey(), 
                SignatureAlgorithm.HS256)
            .compact();
    }

    // ── Extract data from token ───────────────────────────────

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
    }

    // ── Validate token ────────────────────────────────────────

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, 
                                  UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) 
               && !isTokenExpired(token);
    }
}