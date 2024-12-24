package com.example.insightify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.insightify.model.Favorite;
import com.example.insightify.service.FavoriteService;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritesController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addFavorite(@RequestBody Favorite favorite) {
        System.out.println(">>> FavoritesController.addFavorite():"
            + " userId=" + (favorite.getUser() != null ? favorite.getUser().getId() : "null")
            + ", articleId=" + favorite.getArticleId()
            + ", articleTitle=" + favorite.getArticleTitle()
            + ", articleUrl=" + favorite.getArticleUrl()
        );
        Favorite savedFavorite = favoriteService.addFavorite(favorite);
        System.out.println(">>> FavoritesController.addFavorite(): savedFavorite.id=" + savedFavorite.getId());

        return ResponseEntity.ok(savedFavorite);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeFavorite(@PathVariable Long id) {
        System.out.println(">>> FavoritesController.removeFavorite(): id=" + id);
        favoriteService.removeFavorite(id);
        return ResponseEntity.ok("Favorite removed successfully!");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Favorite>> getUserFavorites(@RequestParam Long userId) {
        System.out.println(">>> FavoritesController.getUserFavorites(): userId=" + userId);
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        System.out.println(">>> Found favorites: " + favorites.size());
        return ResponseEntity.ok(favorites);
    }
}
