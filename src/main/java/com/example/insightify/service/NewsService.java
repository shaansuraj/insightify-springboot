package com.example.insightify.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.insightify.model.Article;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Value("${newsapi.baseurl}")
    private String baseUrl;

    @Value("${newsapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Article> getNewsByCategory(String category) {
        // Corrected URL to NewsAPI
        String url = String.format("%s/top-headlines?country=us&category=%s&apiKey=%s", baseUrl, category, apiKey);
        
        // Log the constructed URL for debugging
        logger.info("Requesting NewsAPI with URL: {}", url);
    
        List<Article> articles = new ArrayList<>();
        try {
            String response = restTemplate.getForObject(url, String.class);
            
            // Log the raw response for debugging
            logger.info("Raw NewsAPI Response: {}", response);
    
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
    
            if (root.has("articles")) {
                for (JsonNode node : root.get("articles")) {
                    Article article = mapper.treeToValue(node, Article.class);
                    articles.add(article);
                }
            }
        } catch (Exception e) {
            logger.error("Error fetching news from NewsAPI", e);
        }
        return articles;
    }
}
