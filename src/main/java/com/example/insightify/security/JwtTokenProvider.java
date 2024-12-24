// src/main/java/com/example/insightify/security/JwtTokenProvider.java

package com.example.insightify.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    // NEW: Generate a token that has "role" and "userId" in its payload
    public String generateToken(String username, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)            // "sub"
                .claim("role", role)            // e.g. "ROLE_USER"
                .claim("userId", userId)        // store user ID
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(
                    Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)),
                    SignatureAlgorithm.HS512
                )
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject(); // e.g. "test1@gmail.com"
    }

    // Optional helper if you want to fetch userId or role in the filter
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.get("userId", Long.class);
    }

    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.get("role", String.class);
    }
}