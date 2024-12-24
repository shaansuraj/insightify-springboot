package com.example.insightify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.insightify.repository")
public class InsightifyApplication {

    private static final Logger logger = LoggerFactory.getLogger(InsightifyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(InsightifyApplication.class, args);
        logger.info("Insightify application started successfully!");  // Log app startup
    }
}
