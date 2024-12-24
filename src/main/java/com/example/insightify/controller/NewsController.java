package com.example.insightify.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.insightify.model.Article;
import com.example.insightify.service.NewsService;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /**
     * CHANGED:
     * Previously, we had @GetMapping("/{category}") with a @PathVariable.
     * Now we use @GetMapping (no path) + @RequestParam("category") to match /news?category=XYZ.
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Article>> getNewsByQueryParam(@RequestParam String category) {
        logger.info("Received category query param: {}", category);
        List<Article> articles = newsService.getNewsByCategory(category);
        logger.info("Fetched {} articles for category: {}", articles.size(), category);
        return ResponseEntity.ok(articles);
    }

    /**
     * REMOVED or COMMENTED OUT:
     * Old approach with path variable:
     *   @GetMapping("/{category}")
     *   public ResponseEntity<List<Article>> getNewsByCategory(@PathVariable String category) { ... }
     */
}