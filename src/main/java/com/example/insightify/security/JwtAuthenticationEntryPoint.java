package com.example.insightify.security; // Specifies the package where the class resides, typically a security-related package.

import java.io.IOException; // Imports the IOException class to handle input/output errors, specifically for the response stream.

import org.springframework.security.core.AuthenticationException; // Imports AuthenticationException, which is thrown when authentication fails.
import org.springframework.security.web.AuthenticationEntryPoint; // Imports AuthenticationEntryPoint, used to handle unauthorized access attempts.
import org.springframework.stereotype.Component; // Imports @Component annotation, which marks the class as a Spring bean for dependency injection.

import jakarta.servlet.http.HttpServletRequest; // Imports HttpServletRequest, used to handle the incoming HTTP request.
import jakarta.servlet.http.HttpServletResponse; // Imports HttpServletResponse, used to manipulate the outgoing HTTP response.

@Component // Marks this class as a Spring component, allowing it to be managed by Spring's IoC container.
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint { // Implements AuthenticationEntryPoint, which handles unauthorized access attempts.

    @Override
    public void commence( // Overrides the commence method from AuthenticationEntryPoint, which is called when authentication fails.
            HttpServletRequest request, // The incoming HTTP request object.
            HttpServletResponse response, // The outgoing HTTP response object.
            AuthenticationException authException // The exception thrown when authentication fails.
    ) throws IOException { // Declares that an IOException might be thrown during the response handling.

        // Sends an HTTP 401 Unauthorized error response to the client with a custom message.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // SC_UNAUTHORIZED (401) is a status code indicating unauthorized access.
    }
}
