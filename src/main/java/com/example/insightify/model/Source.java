// Package declaration
package com.example.insightify.model;

// This class represents a "Source" which could be a news source or a data provider in the system.
public class Source {

    // The id of the source (e.g., an identifier for the news source)
    private String id;

    // The name of the source (e.g., the name of the news provider)
    private String name;

    // Default constructor for Jackson deserialization
    // This constructor is needed by frameworks like Jackson for automatic object creation
    // during the deserialization of JSON data (such as when fetching data from an API)
    public Source() {}

    // Getter method for the id field
    // This method returns the id of the source
    public String getId() {
        return id; // Returns the id of the source
    }

    // Setter method for the id field
    // This method sets the id of the source
    public void setId(String id) {
        this.id = id; // Sets the id of the source
    }

    // Getter method for the name field
    // This method returns the name of the source
    public String getName() {
        return name; // Returns the name of the source
    }

    // Setter method for the name field
    // This method sets the name of the source
    public void setName(String name) {
        this.name = name; // Sets the name of the source
    }
}
