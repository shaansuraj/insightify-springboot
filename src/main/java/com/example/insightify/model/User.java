// Package declaration
package com.example.insightify.model;

// Importing necessary JPA annotations
import jakarta.persistence.Column;      // To define column properties for database mapping
import jakarta.persistence.Entity;      // Marks this class as an entity for JPA
import jakarta.persistence.GeneratedValue; // Specifies the generation strategy for primary keys
import jakarta.persistence.GenerationType; // Defines how the primary key will be generated
import jakarta.persistence.Id;         // Marks the primary key of the entity

// Marks this class as a JPA entity that corresponds to a table in the database
@Entity
public class User {

    // @Id annotation marks the id field as the primary key of the entity
    @Id
    // @GeneratedValue annotation specifies how the id field will be automatically generated
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses the identity strategy for primary key generation (auto-increment)
    private Long id; // The primary key of the User entity

    // @Column annotation defines properties for the column in the database
    // `nullable = false` ensures that this field cannot be null in the database
    // `unique = true` ensures that the username must be unique across all users
    @Column(nullable = false, unique = true)
    private String username; // The username of the user (unique and not null)

    // `nullable = false` ensures that this field cannot be null in the database
    @Column(nullable = false)
    private String password; // The password of the user (not null)

    // `nullable = false` ensures that this field cannot be null in the database
    // `unique = true` ensures that the email must be unique across all users
    @Column(nullable = false, unique = true)
    private String email; // The email of the user (unique and not null)

    // Default constructor (required by JPA for entity instantiation)
    // It allows JPA to instantiate the User object when retrieving data from the database
    public User() {
    }

    // Constructor to initialize the User object with a username, password, and email
    public User(String username, String password, String email) {
        this.username = username;  // Set the username
        this.password = password;  // Set the password
        this.email = email;        // Set the email
    }

    // Getter method for the id field
    public Long getId() {
        return id; // Returns the id of the user
    }

    // Setter method for the id field
    public void setId(Long id) {
        this.id = id; // Sets the id of the user
    }

    // Getter method for the username field
    public String getUsername() {
        return username; // Returns the username of the user
    }

    // Setter method for the username field
    public void setUsername(String username) {
        this.username = username; // Sets the username of the user
    }

    // Getter method for the password field
    public String getPassword() {
        return password; // Returns the password of the user
    }

    // Setter method for the password field
    public void setPassword(String password) {
        this.password = password; // Sets the password of the user
    }

    // Getter method for the email field
    public String getEmail() {
        return email; // Returns the email of the user
    }

    // Setter method for the email field
    public void setEmail(String email) {
        this.email = email; // Sets the email of the user
    }
}
