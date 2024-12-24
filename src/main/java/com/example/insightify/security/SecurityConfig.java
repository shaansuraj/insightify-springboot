package com.example.insightify.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Construct our JwtFilter, providing JwtTokenProvider
        JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider);

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Permit OPTIONS preflight for /news
                .requestMatchers(HttpMethod.OPTIONS, "/news/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/favorites/**").permitAll()
                .requestMatchers("/favorites/**").hasRole("USER")
                // If your news endpoint is /news?category=..., you can do:
                .requestMatchers(HttpMethod.GET, "/news").hasRole("USER")

                // Permit auth endpoints
                .requestMatchers("/auth/**").permitAll()

                // Everything else must be authenticated
                .anyRequest().authenticated()
            )
            // ADDED: Insert our JwtFilter BEFORE Spring's UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}