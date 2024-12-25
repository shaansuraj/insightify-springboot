// Package declaration: The controller class belongs to the com.example.insightify.controller package
package com.example.insightify.controller;

// Importing necessary classes for the controller
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  // For dependency injection
import org.springframework.http.ResponseEntity;  // For returning HTTP responses
import org.springframework.security.access.prepost.PreAuthorize;  // For role-based access control
import org.springframework.web.bind.annotation.CrossOrigin;  // For handling cross-origin requests
import org.springframework.web.bind.annotation.DeleteMapping;  // For handling DELETE requests
import org.springframework.web.bind.annotation.GetMapping;  // For handling GET requests
import org.springframework.web.bind.annotation.PathVariable;  // For handling path variables in requests
import org.springframework.web.bind.annotation.PostMapping;  // For handling POST requests
import org.springframework.web.bind.annotation.RequestBody;  // For mapping request body to method parameter
import org.springframework.web.bind.annotation.RequestMapping;  // For mapping HTTP requests to handler methods
import org.springframework.web.bind.annotation.RequestParam;  // For handling query parameters in HTTP requests
import org.springframework.web.bind.annotation.RestController;  // Marks the class as a RESTful controller

import com.example.insightify.model.Favorite;
import com.example.insightify.service.FavoriteService;

// @RestController annotation indicates that this class handles HTTP requests and automatically converts responses into JSON or other formats.
@RestController
// @RequestMapping("/favorites") maps HTTP requests to "/favorites" to the methods in this controller.
@RequestMapping("/favorites")
// @CrossOrigin annotation allows cross-origin requests. This is useful when the frontend is hosted on a different origin (e.g., localhost:3000).
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritesController {

    // @Autowired annotation automatically injects an instance of FavoriteService into the controller.
    @Autowired
    private FavoriteService favoriteService;

    /**
     * @PostMapping("/add") maps HTTP POST requests to this method.
     * This method handles the addition of a new favorite article to the user's favorites list.
     * @PreAuthorize ensures that only users with the 'USER' role can access this method.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")  // Ensures that only users with 'USER' role can access this endpoint.
    public ResponseEntity<?> addFavorite(@RequestBody Favorite favorite) {
        // Logging the details of the favorite being added. This helps in tracking the action.
        System.out.println(">>> FavoritesController.addFavorite():"
            + " userId=" + (favorite.getUser() != null ? favorite.getUser().getId() : "null")
            + ", articleId=" + favorite.getArticleId()
            + ", articleTitle=" + favorite.getArticleTitle()
            + ", articleUrl=" + favorite.getArticleUrl()
        );

        // Calling the addFavorite method of FavoriteService to add the favorite to the database.
        Favorite savedFavorite = favoriteService.addFavorite(favorite);

        // Logging the ID of the saved favorite for tracking purposes.
        System.out.println(">>> FavoritesController.addFavorite(): savedFavorite.id=" + savedFavorite.getId());

        // Returning the saved favorite wrapped in a ResponseEntity with HTTP status 200 (OK).
        return ResponseEntity.ok(savedFavorite);
    }

    /**
     * @DeleteMapping("/remove/{id}") maps HTTP DELETE requests to this method.
     * This method removes a favorite article based on its ID.
     * @PreAuthorize ensures that only users with the 'USER' role can access this method.
     */
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('USER')")  // Ensures that only users with 'USER' role can access this endpoint.
    public ResponseEntity<?> removeFavorite(@PathVariable Long id) {
        // Logging the ID of the favorite being removed for tracking purposes.
        System.out.println(">>> FavoritesController.removeFavorite(): id=" + id);

        // Calling the removeFavorite method of FavoriteService to remove the favorite from the database.
        favoriteService.removeFavorite(id);

        // Returning a success message with HTTP status 200 (OK) after successfully removing the favorite.
        return ResponseEntity.ok("Favorite removed successfully!");
    }

    /**
     * @GetMapping("/user") maps HTTP GET requests to this method.
     * This method retrieves the list of favorites for a specific user based on the userId query parameter.
     * @PreAuthorize ensures that only users with the 'USER' role can access this method.
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")  // Ensures that only users with 'USER' role can access this endpoint.
    public ResponseEntity<List<Favorite>> getUserFavorites(@RequestParam Long userId) {
        // Logging the userId for which the favorites are being fetched for tracking purposes.
        System.out.println(">>> FavoritesController.getUserFavorites(): userId=" + userId);

        // Calling the getUserFavorites method of FavoriteService to retrieve the list of favorites for the user.
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);

        // Logging the number of favorites found for the user for debugging purposes.
        System.out.println(">>> Found favorites: " + favorites.size());

        // Returning the list of favorites wrapped in a ResponseEntity with HTTP status 200 (OK).
        return ResponseEntity.ok(favorites);
    }
}
