// Package declaration
package com.example.insightify.model;

// This class represents an "Article" entity which could be a news article or content provided by a source.
public class Article {

    // The Source object representing the origin of the article (e.g., the news outlet or API).
    private Source source;

    // The author of the article.
    private String author;

    // The title of the article.
    private String title;

    // A brief description or excerpt from the article.
    private String description;

    // The URL of the article.
    private String url;

    // The URL of an image associated with the article (such as a thumbnail or cover image).
    private String urlToImage;

    // The date and time when the article was published.
    private String publishedAt;

    // The full content of the article.
    private String content;

    // Default constructor for Jackson deserialization
    // This constructor is needed by frameworks like Jackson for automatic object creation
    // during the deserialization of JSON data (e.g., from an API response).
    public Article() {}

    // Getter method for the source field
    // This method returns the source of the article, which is an object of the Source class.
    public Source getSource() {
        return source; // Returns the source of the article
    }

    // Setter method for the source field
    // This method sets the source of the article, which is an object of the Source class.
    public void setSource(Source source) {
        this.source = source; // Sets the source of the article
    }

    // Getter method for the author field
    // This method returns the author of the article.
    public String getAuthor() {
        return author; // Returns the author of the article
    }

    // Setter method for the author field
    // This method sets the author of the article.
    public void setAuthor(String author) {
        this.author = author; // Sets the author of the article
    }

    // Getter method for the title field
    // This method returns the title of the article.
    public String getTitle() {
        return title; // Returns the title of the article
    }

    // Setter method for the title field
    // This method sets the title of the article.
    public void setTitle(String title) {
        this.title = title; // Sets the title of the article
    }

    // Getter method for the description field
    // This method returns the description of the article.
    public String getDescription() {
        return description; // Returns the description of the article
    }

    // Setter method for the description field
    // This method sets the description of the article.
    public void setDescription(String description) {
        this.description = description; // Sets the description of the article
    }

    // Getter method for the url field
    // This method returns the URL of the article.
    public String getUrl() {
        return url; // Returns the URL of the article
    }

    // Setter method for the url field
    // This method sets the URL of the article.
    public void setUrl(String url) {
        this.url = url; // Sets the URL of the article
    }

    // Getter method for the urlToImage field
    // This method returns the URL of the image associated with the article.
    public String getUrlToImage() {
        return urlToImage; // Returns the URL of the article's image
    }

    // Setter method for the urlToImage field
    // This method sets the URL of the image associated with the article.
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage; // Sets the URL of the article's image
    }

    // Getter method for the publishedAt field
    // This method returns the published date of the article.
    public String getPublishedAt() {
        return publishedAt; // Returns the published date of the article
    }

    // Setter method for the publishedAt field
    // This method sets the published date of the article.
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt; // Sets the published date of the article
    }

    // Getter method for the content field
    // This method returns the full content of the article.
    public String getContent() {
        return content; // Returns the full content of the article
    }

    // Setter method for the content field
    // This method sets the full content of the article.
    public void setContent(String content) {
        this.content = content; // Sets the full content of the article
    }
}
