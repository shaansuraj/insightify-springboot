// Package declaration: The controller class belongs to the com.example.insightify.controller package
package com.example.insightify.controller;

// Importing necessary classes
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;  // For dependency injection
import org.springframework.http.ResponseEntity;  // For returning HTTP responses
import org.springframework.security.access.prepost.PreAuthorize;  // For role-based access control
import org.springframework.web.bind.annotation.CrossOrigin;  // For handling cross-origin requests
import org.springframework.web.bind.annotation.GetMapping;  // For handling GET requests
import org.springframework.web.bind.annotation.RequestMapping;  // For mapping HTTP requests to handler methods
import org.springframework.web.bind.annotation.RequestParam;  // For handling query parameters in HTTP requests
import org.springframework.web.bind.annotation.RestController;  // Marks the class as a RESTful controller

import com.example.insightify.model.Article;
import com.example.insightify.service.NewsService;
// The @RestController annotation indicates that this class will handle HTTP requests
// and automatically convert responses into JSON or other formats to be sent to the client.
@RestController
// @RequestMapping("/news") maps the "/news" endpoint to the methods in this class.
@RequestMapping("/news")
// @CrossOrigin allows cross-origin requests. This is useful for enabling frontend applications
// (for example, running on localhost:3000) to make requests to the backend running on a different origin.
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {

    // Logger instance to log information and debug messages related to this class
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    // The @Autowired annotation is used for automatic dependency injection. Spring will automatically
    // provide an instance of NewsService to the NewsController when it's needed.
    @Autowired
    private NewsService newsService;

    /**
     * @GetMapping maps HTTP GET requests to this method.
     * This method handles requests to /news?category=XYZ, where XYZ is a query parameter.
     * The @PreAuthorize annotation ensures that only users with the 'USER' role can access this method.
     */
    @GetMapping  // Maps GET requests to this method. It will be called for requests like /news?category=technology.
    @PreAuthorize("hasRole('USER')")  // Ensures that only users with the 'USER' role can access this method.
    public ResponseEntity<List<Article>> getNewsByQueryParam(@RequestParam String category) {
        // Logging the received category parameter for debugging purposes.
        // This helps track what category is being requested.
        logger.info("Received category query param: {}", category);

        // Fetching a list of articles based on the category provided in the query parameter.
        // The newsService.getNewsByCategory(category) method retrieves the articles from the service layer.
        List<Article> articles = newsService.getNewsByCategory(category);

        // Logging the number of articles fetched for the requested category.
        // This is useful for debugging and tracking the performance of the request.
        logger.info("Fetched {} articles for category: {}", articles.size(), category);

        // Returning the fetched articles as a ResponseEntity. This ResponseEntity will automatically
        // be converted into JSON and sent back to the client.
        // The HTTP status code 200 (OK) is implied since ResponseEntity.ok() is used.
        return ResponseEntity.ok(articles);
    }
}
