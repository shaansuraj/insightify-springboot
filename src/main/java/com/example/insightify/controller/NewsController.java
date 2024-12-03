package com.example.insightify.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.insightify.model.Article;
import com.example.insightify.service.NewsService;

@RestController
@CrossOrigin(origins = "*")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public List<Article> getNews(@RequestParam String category) {
        logger.info("Received category: {}", category);  // Log the received category
        List<Article> articles = newsService.getNewsByCategory(category);
        logger.info("Fetched {} articles for category: {}", articles.size(), category);  // Log the number of articles fetched
        return articles;
    }
}
