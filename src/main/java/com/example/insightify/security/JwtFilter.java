package com.example.insightify.security;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtTokenProvider jwtTokenProvider; // CHANGED: prefer constructor injection

    // CHANGED: Provide a constructor so we can pass JwtTokenProvider from SecurityConfig
    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Only validate token if NOT /auth/signup or /auth/login
        if (!path.startsWith("/auth/signup") && !path.startsWith("/auth/login")) {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                if (jwtTokenProvider.validateToken(token)) {
                    // CHANGED: get username & role from token
                    String username = jwtTokenProvider.getUsername(token);
                    String role = jwtTokenProvider.getRole(token);

                    logger.info("Token validated for user: {}, with role: {}", username, role);

                    // CHANGED: set role in authorities
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.singletonList(authority)
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    logger.info("Invalid token: " + token);
                }
            } else {
                logger.info("Authorization header not found or doesn't start with 'Bearer '");
            }
        }

        filterChain.doFilter(request, response);
    }
}