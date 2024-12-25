package com.example.insightify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.insightify.model.Favorite; // Model for favorite articles
import com.example.insightify.model.User; // Model for users
import com.example.insightify.repository.FavoriteRepository; // Repository for interacting with the favorite articles DB
import com.example.insightify.repository.UserRepository; // Repository for interacting with the users DB

@Service // Marks the class as a service component to be managed by Spring
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository; // Injecting the FavoriteRepository to interact with the database

    // ADDED: Injecting the UserRepository to fetch user details from the database
    @Autowired
    private UserRepository userRepository;

    // Method to add a new favorite
    public Favorite addFavorite(Favorite favorite) {
        Long userId = favorite.getUser().getId(); // Extract the user ID from the favorite object
        System.out.println(">>> FavoriteService.addFavorite(): userId=" + userId);
        
        // Fetch the user from the database
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found, id=" + userId));

        // Set the fetched user entity to the favorite object to link them
        favorite.setUser(existingUser);

        // Save the favorite object in the database
        Favorite saved = favoriteRepository.save(favorite);
        System.out.println(">>> FavoriteService.addFavorite(): Favorite inserted with id=" + saved.getId());

        return saved; // Return the saved favorite
    }

    // Method to remove a favorite by its ID
    public void removeFavorite(Long id) {
        System.out.println(">>> FavoriteService.removeFavorite(): id=" + id);

        // Delete the favorite from the database using the ID
        favoriteRepository.deleteById(id);
    }

    // Method to retrieve a user's favorites by user ID
    public List<Favorite> getUserFavorites(Long userId) {
        System.out.println(">>> FavoriteService.getUserFavorites(): userId=" + userId);

        // Fetch all favorites for the given user ID
        return favoriteRepository.findByUserId(userId);
    }
}
