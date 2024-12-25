package com.example.insightify.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.insightify.model.User;
import com.example.insightify.service.AuthService;

@RestController
// @CrossOrigin allows cross-origin requests from "http://localhost:3000" to the controller methods, which is useful for frontend-backend communication.
@CrossOrigin(origins = "http://localhost:3000")
// @RequestMapping("/auth") maps HTTP requests starting with "/auth" to methods in this controller.
@RequestMapping("/auth")
public class AuthController {

    // @Autowired automatically injects an instance of AuthService to handle the authentication logic.
    @Autowired
    private AuthService authService;

    /**
     * @PostMapping("/signup") maps HTTP POST requests to "/auth/signup" to this method.
     * This method handles user registration by receiving a User object and passing it to the authService for registration.
     */
    @PostMapping("/signup")
    public String register(@RequestBody User user) {
        // Calls the authService to register the user and returns a response.
        return authService.register(user);
    }

    /**
     * @PostMapping("/login") maps HTTP POST requests to "/auth/login" to this method.
     * This method handles user login by receiving a User object, authenticating it, and returning a token.
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        // Calls the authService to log the user in and retrieve the authentication token.
        String token = authService.login(user);

        // Creates a response map with the token to be returned in the response body.
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        // Returns the response map with the token.
        return response;
    }
}
