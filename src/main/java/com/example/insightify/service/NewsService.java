package com.example.insightify.service;

import java.util.ArrayList; // Importing ArrayList to store articles
import java.util.List; // Importing List interface to hold multiple articles

import org.slf4j.Logger; // Logger interface from SLF4J for logging
import org.slf4j.LoggerFactory; // LoggerFactory to create a logger instance
import org.springframework.beans.factory.annotation.Value; // @Value annotation to inject values from configuration
import org.springframework.stereotype.Service; // @Service annotation to mark the class as a Spring service
import org.springframework.web.client.RestTemplate; // RestTemplate for making HTTP requests

import com.example.insightify.model.Article; // Importing the Article model class to map API response data
import com.fasterxml.jackson.databind.JsonNode; // JsonNode class from Jackson for parsing JSON
import com.fasterxml.jackson.databind.ObjectMapper; // ObjectMapper from Jackson for converting JSON to Java objects

@Service // Marks the class as a Spring-managed service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class); // Logger for debugging

    @Value("${newsapi.baseurl}") // Injects the base URL of the NewsAPI from application properties
    private String baseUrl;

    @Value("${newsapi.key}") // Injects the API key for accessing NewsAPI
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate for making HTTP requests

    // Method to fetch news based on the category
    public List<Article> getNewsByCategory(String category) {
        // Construct the URL for NewsAPI request
        // Example URL: https://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey=xyz
        String url = String.format("%s/top-headlines?country=us&category=%s&apiKey=%s", baseUrl, category, apiKey);
        
        // Log the constructed URL for debugging purposes
        logger.info("Requesting NewsAPI with URL: {}", url);
    
        List<Article> articles = new ArrayList<>(); // List to hold articles

        try {
            // Make the API call and get the response as a String
            String response = restTemplate.getForObject(url, String.class);
            
            // Log the raw response from NewsAPI
            logger.info("Raw NewsAPI Response: {}", response);
    
            ObjectMapper mapper = new ObjectMapper(); // JSON mapper to convert response to objects
            JsonNode root = mapper.readTree(response); // Parse the response JSON

            // Check if the response contains the "articles" field
            if (root.has("articles")) {
                // Loop through the articles and map each to an Article object
                for (JsonNode node : root.get("articles")) {
                    Article article = mapper.treeToValue(node, Article.class);
                    articles.add(article); // Add the article to the list
                }
            }
        } catch (Exception e) {
            // Log any errors that occur during the API call or response parsing
            logger.error("Error fetching news from NewsAPI", e);
        }
        return articles; // Return the list of articles
    }
}
