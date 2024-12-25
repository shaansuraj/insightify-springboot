package com.example.insightify.config;

import org.springframework.context.annotation.Configuration; // Imports the @Configuration annotation for marking the class as a configuration class.
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Imports the CorsRegistry class used to configure CORS mappings.
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Imports the WebMvcConfigurer interface to customize Spring MVC configuration.

@Configuration // Marks this class as a configuration class, so Spring Boot knows to process it during application startup.
public class WebConfig implements WebMvcConfigurer { // Implements WebMvcConfigurer to customize Spring MVC configuration, specifically for CORS settings.

    @Override // Indicates that this method overrides a method from the WebMvcConfigurer interface.
    public void addCorsMappings(CorsRegistry registry) {
        registry // Begins the method chain for configuring CORS (Cross-Origin Resource Sharing) settings.
            .addMapping("/") // Configures CORS mappings for the root path ("/"), allowing access from other origins.
            .allowedOrigins("http://localhost:3000") // Specifies that only the React development server running on "http://localhost:3000" is allowed to make requests.
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allows only the specified HTTP methods: GET, POST, PUT, DELETE, and OPTIONS.
            .allowedHeaders("*"); // Allows all HTTP headers in the request.
            //.allowCredentials(true); // Uncomment if you want to allow sending credentials (cookies, authorization headers, etc.) with the request.
    }
}
