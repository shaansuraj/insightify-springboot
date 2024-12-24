package com.example.insightify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.insightify.model.Favorite;
import com.example.insightify.model.User;
import com.example.insightify.repository.FavoriteRepository;
import com.example.insightify.repository.UserRepository;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    // ADDED: To load the actual User from DB
    @Autowired
    private UserRepository userRepository;

    public Favorite addFavorite(Favorite favorite) {
        Long userId = favorite.getUser().getId();
        System.out.println(">>> FavoriteService.addFavorite(): userId=" + userId);
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found, id=" + userId));

        // Use the managed user entity
        favorite.setUser(existingUser);
        Favorite saved = favoriteRepository.save(favorite);
        System.out.println(">>> FavoriteService.addFavorite(): Favorite inserted with id=" + saved.getId());


        return saved;
        
    }

    public void removeFavorite(Long id) {
        System.out.println(">>> FavoriteService.removeFavorite(): id=" + id);

        favoriteRepository.deleteById(id);
    }

    public List<Favorite> getUserFavorites(Long userId) {
        System.out.println(">>> FavoriteService.getUserFavorites(): userId=" + userId);

        return favoriteRepository.findByUserId(userId);
    }
}