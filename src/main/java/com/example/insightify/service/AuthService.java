// src/main/java/com/example/insightify/service/AuthService.java

package com.example.insightify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.insightify.model.User;
import com.example.insightify.repository.UserRepository;
import com.example.insightify.security.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String register(User user) {
        // Save user, etc.
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            // The user is legit. Now embed "ROLE_USER" and userId in the token
            Long userId = existingUser.getId(); // or however you get the user ID
            return jwtTokenProvider.generateToken(
                existingUser.getEmail(),
                "ROLE_USER",      // single role
                userId            // store their DB ID
            );
        } else {
            throw new RuntimeException("Invalid credentials!");
        }
    }
}