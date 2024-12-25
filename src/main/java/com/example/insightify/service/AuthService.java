package com.example.insightify.service;

import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.stereotype.Service; // Marks the class as a service layer component

import com.example.insightify.model.User; // User model class
import com.example.insightify.repository.UserRepository; // User repository for DB interaction
import com.example.insightify.security.JwtTokenProvider; // JWT utility for generating tokens

@Service // Marks the class as a service to be managed by Spring
public class AuthService {

    @Autowired
    private UserRepository userRepository; // Injecting the UserRepository to interact with the database

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Injecting JwtTokenProvider to generate JWT tokens

    // Register a new user in the system
    public String register(User user) {
        // Save the user in the database
        userRepository.save(user);
        return "User registered successfully!"; // Return success message
    }

    // Handle login and return a JWT token if credentials are valid
    public String login(User user) {
        // Find the user by email
        User existingUser = userRepository.findByEmail(user.getEmail());

        // Check if user exists and password matches
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            // User is valid, generate JWT token with role and user ID
            Long userId = existingUser.getId(); // Get the user ID from the database
            return jwtTokenProvider.generateToken(
                existingUser.getEmail(), // Pass the email as the username
                "ROLE_USER",      // Assign the role "ROLE_USER"
                userId            // Include the user ID in the token's payload
            );
        } else {
            // If credentials are invalid, throw an exception
            throw new RuntimeException("Invalid credentials!");
        }
    }
}
