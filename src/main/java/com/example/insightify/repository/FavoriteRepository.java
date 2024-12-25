package com.example.insightify.repository; // Specifies the package where the repository class resides.

import java.util.List; // Imports the List interface from java.util package, used to return multiple Favorite objects.

import org.springframework.data.jpa.repository.JpaRepository; // Imports JpaRepository, providing basic CRUD operations for the Favorite entity.
import org.springframework.stereotype.Repository; // Imports the @Repository annotation to indicate this interface is a repository for Spring Data JPA.

import com.example.insightify.model.Favorite; // Imports the Favorite model class, which is the entity type managed by the repository.

@Repository // Marks this interface as a Spring Data JPA repository, making it eligible for dependency injection and database interaction.
public interface FavoriteRepository extends JpaRepository<Favorite, Long> { // Declares the FavoriteRepository interface, extending JpaRepository to enable CRUD operations on Favorite entities.

    // Custom query method to find all favorites by a specific userId.
    List<Favorite> findByUserId(Long userId); // Returns a list of Favorite objects for a specific user by their userId.

    // Custom query method to find a specific favorite by userId and articleId.
    Favorite findByUserIdAndArticleId(Long userId, String articleId); // Returns a single Favorite object for a specific user and article combination.
}
