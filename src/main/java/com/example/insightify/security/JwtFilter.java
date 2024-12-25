package com.example.insightify.security; // Specifies the package where the class resides, typically a security-related package.

import java.io.IOException; // Imports the IOException class to handle input/output errors in case of issues with the filter chain or request/response handling.
import java.util.Collections; // Imports Collections to work with immutable lists, specifically for authorities.

import org.slf4j.Logger; // Imports the Logger class for logging purposes.
import org.slf4j.LoggerFactory; // Imports LoggerFactory to create an instance of the Logger.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Imports UsernamePasswordAuthenticationToken, a core Spring Security class for representing authenticated users.
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Imports SimpleGrantedAuthority, which is used to represent the user's role.
import org.springframework.security.core.context.SecurityContextHolder; // Imports SecurityContextHolder to store and retrieve the authentication information.
import org.springframework.web.filter.OncePerRequestFilter; // Imports OncePerRequestFilter, a Spring Security filter that ensures this filter is applied once per request.

import jakarta.servlet.FilterChain; // Imports FilterChain to allow further processing of the filter chain after this filter has executed.
import jakarta.servlet.ServletException; // Imports ServletException to handle servlet-related errors.
import jakarta.servlet.http.HttpServletRequest; // Imports HttpServletRequest to get details of the HTTP request.
import jakarta.servlet.http.HttpServletResponse; // Imports HttpServletResponse to manipulate the HTTP response.

public class JwtFilter extends OncePerRequestFilter { // JwtFilter extends OncePerRequestFilter, ensuring it's executed once per request.

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class); // Creates a logger instance for this class.

    private final JwtTokenProvider jwtTokenProvider; // The JwtTokenProvider instance, which is responsible for generating and validating JWT tokens.

    // Constructor to inject JwtTokenProvider, a service that handles JWT-related logic.
    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider; // Assigns the provided JwtTokenProvider to the instance variable.
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) 
            throws ServletException, IOException { // Overrides doFilterInternal to implement the logic for filtering requests.

        String path = request.getRequestURI(); // Gets the URI of the request.

        // Only validate the token if the request is NOT for signup or login.
        if (!path.startsWith("/auth/signup") && !path.startsWith("/auth/login")) {
            String header = request.getHeader("Authorization"); // Retrieves the Authorization header from the request.
            
            // Checks if the Authorization header is present and starts with "Bearer ".
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7); // Extracts the JWT token from the header (removes "Bearer ").

                // Validates the token using JwtTokenProvider.
                if (jwtTokenProvider.validateToken(token)) {
                    // Extracts the username and role from the valid token.
                    String username = jwtTokenProvider.getUsername(token);
                    String role = jwtTokenProvider.getRole(token);

                    logger.info("Token validated for user: {}, with role: {}", username, role); // Logs that the token is validated and the user's role.

                    // Creates a SimpleGrantedAuthority for the user's role.
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                    // Creates an authenticated token with the username and role.
                    UsernamePasswordAuthenticationToken auth = 
                            new UsernamePasswordAuthenticationToken(
                                    username, 
                                    null, 
                                    Collections.singletonList(authority) // Sets the role as the authority of the user.
                            );

                    // Sets the authentication object in the SecurityContextHolder.
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    logger.info("Invalid token: " + token); // Logs that the token is invalid.
                }
            } else {
                logger.info("Authorization header not found or doesn't start with 'Bearer '"); // Logs if the Authorization header is missing or not properly formatted.
            }
        }

        // Passes the request and response to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}
