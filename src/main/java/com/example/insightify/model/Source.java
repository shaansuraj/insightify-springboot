package com.example.insightify.model;

public class Source {

    private String id;
    private String name;

    // Default constructor for Jackson deserialization
    public Source() {}

    // Getter and setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
