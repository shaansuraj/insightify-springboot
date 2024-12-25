// Package declaration
package com.example.insightify.model;

// Importing necessary JPA annotations and classes
import jakarta.persistence.Column;      // To define the column properties in the database
import jakarta.persistence.Entity;      // Marks this class as an entity for JPA
import jakarta.persistence.GeneratedValue; // Specifies that the value of this field is auto-generated
import jakarta.persistence.GenerationType; // Defines the strategy used to generate the primary key
import jakarta.persistence.Id;         // Marks the primary key of the entity
import jakarta.persistence.JoinColumn;  // Specifies the column for the foreign key
import jakarta.persistence.ManyToOne;   // Defines a many-to-one relationship between entities

// Marks this class as a JPA entity that corresponds to a table in the database
@Entity
public class Favorite {

    // @Id annotation marks the id field as the primary key of the entity
    @Id
    // @GeneratedValue indicates that the value of this field will be automatically generated
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity strategy for primary key generation (auto-increment)
    private Long id; // The primary key of the Favorite entity

    // @ManyToOne defines a many-to-one relationship with the User entity
    // @JoinColumn specifies the foreign key column that will reference the User entity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column named "user_id" to establish relationship with User
    private User user; // Reference to the User entity that this favorite belongs to

    // @Column defines the properties of the column in the database
    @Column(nullable = false) // Ensures that articleId cannot be null
    private String articleId; // The unique identifier of the article being favorited

    @Column(nullable = false) // Ensures that articleTitle cannot be null
    private String articleTitle; // The title of the article being favorited

    @Column(nullable = false) // Ensures that articleUrl cannot be null
    private String articleUrl; // The URL of the article being favorited

    // Default constructor (required by JPA for entity instantiation)
    public Favorite() {}

    // Constructor to initialize the Favorite object with a User and article details
    public Favorite(User user, String articleId, String articleTitle, String articleUrl) {
        this.user = user;               // Set the user who favorited the article
        this.articleId = articleId;     // Set the article's unique identifier
        this.articleTitle = articleTitle; // Set the article's title
        this.articleUrl = articleUrl;   // Set the article's URL
    }

    // Getter method for the id field
    public Long getId() {
        return id; // Returns the ID of the favorite
    }

    // Setter method for the id field
    public void setId(Long id) {
        this.id = id; // Sets the ID of the favorite
    }

    // Getter method for the user field
    public User getUser() {
        return user; // Returns the user associated with this favorite
    }

    // Setter method for the user field
    public void setUser(User user) {
        this.user = user; // Sets the user associated with this favorite
    }

    // Getter method for the articleId field
    public String getArticleId() {
        return articleId; // Returns the article ID
    }

    // Setter method for the articleId field
    public void setArticleId(String articleId) {
        this.articleId = articleId; // Sets the article ID
    }

    // Getter method for the articleTitle field
    public String getArticleTitle() {
        return articleTitle; // Returns the article title
    }

    // Setter method for the articleTitle field
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle; // Sets the article title
    }

    // Getter method for the articleUrl field
    public String getArticleUrl() {
        return articleUrl; // Returns the article URL
    }

    // Setter method for the articleUrl field
    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl; // Sets the article URL
    }
}
