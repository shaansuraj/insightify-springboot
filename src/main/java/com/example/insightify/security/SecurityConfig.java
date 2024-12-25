package com.example.insightify.security;

import org.springframework.beans.factory.annotation.Autowired; // For injecting the JwtTokenProvider bean
import org.springframework.context.annotation.Bean; // To define beans for the Spring context
import org.springframework.context.annotation.Configuration; // Marks this class as a configuration class
import org.springframework.http.HttpMethod; // For HTTP method types (GET, POST, etc.)
import org.springframework.security.config.Customizer; // For customizing security settings
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // For configuring HTTP security
import org.springframework.security.web.SecurityFilterChain; // To define security filters (including JWT filters)
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // To add a filter before the default authentication filter

@Configuration // Marks this class as a configuration class for Spring's context
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Injecting JwtTokenProvider to use for JWT token validation

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Construct our JwtFilter, providing JwtTokenProvider
        JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider); // Creating an instance of JwtFilter and passing the JwtTokenProvider

        http
            .cors(Customizer.withDefaults()) // Enables Cross-Origin Resource Sharing (CORS) with default configuration
            .csrf(csrf -> csrf.disable()) // Disables Cross-Site Request Forgery (CSRF) protection (could be needed for APIs)
            .authorizeHttpRequests(authz -> authz
                // Permit OPTIONS preflight requests for /news and /favorites endpoints
                .requestMatchers(HttpMethod.OPTIONS, "/news/**").permitAll() 
                .requestMatchers(HttpMethod.OPTIONS, "/favorites/**").permitAll()

                // Protect /favorites endpoints, only allowing users with the "USER" role
                .requestMatchers("/favorites/**").hasRole("USER")
                
                // Protect /news endpoint, requiring "USER" role for GET requests
                .requestMatchers(HttpMethod.GET, "/news").hasRole("USER")

                // Allow all requests to /auth endpoints (signup, login, etc.)
                .requestMatchers("/auth/**").permitAll()

                // Everything else must be authenticated
                .anyRequest().authenticated()
            )
            // ADDED: Insert our JwtFilter BEFORE Spring's default UsernamePasswordAuthenticationFilter to intercept and process JWT tokens first
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Return the built SecurityFilterChain
    }
}
