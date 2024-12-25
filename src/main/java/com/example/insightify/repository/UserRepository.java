package com.example.insightify.repository; // Specifies the package where the repository class resides.

import org.springframework.data.jpa.repository.JpaRepository; // Imports JpaRepository, which provides basic CRUD operations for the User entity.
import org.springframework.stereotype.Repository; // Imports @Repository annotation, indicating this interface is a repository for Spring Data JPA.

import com.example.insightify.model.User; // Imports the User model class, which is the entity type managed by the repository.

@Repository // Marks this interface as a Spring Data JPA repository, making it eligible for dependency injection and database interaction.
public interface UserRepository extends JpaRepository<User, Long> { // Declares the UserRepository interface, extending JpaRepository to enable CRUD operations on User entities.

    // Custom query method to find a user by their email.
    User findByEmail(String email); // Retrieves a User object based on the email provided. If no user is found, it returns null.
}
