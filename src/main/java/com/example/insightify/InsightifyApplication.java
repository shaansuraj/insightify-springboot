package com.example.insightify;

import org.slf4j.Logger; // SLF4J Logger interface for logging messages
import org.slf4j.LoggerFactory; // LoggerFactory used to create logger instances
import org.springframework.boot.SpringApplication; // SpringApplication class used to run the Spring Boot application
import org.springframework.boot.autoconfigure.SpringBootApplication; // Annotation to mark the class as the entry point for the Spring Boot application
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // Annotation to enable JPA repositories in a specified base package

@SpringBootApplication // Marks the class as the main entry point for the Spring Boot application
@EnableJpaRepositories(basePackages = "com.example.insightify.repository") // Enables JPA repositories from the specified package
public class InsightifyApplication {

    private static final Logger logger = LoggerFactory.getLogger(InsightifyApplication.class); // Logger to log messages related to application startup

    public static void main(String[] args) {
        SpringApplication.run(InsightifyApplication.class, args); // Starts the Spring Boot application
        logger.info("Insightify application started successfully!");  // Log a message to confirm that the application started successfully
    }
}
