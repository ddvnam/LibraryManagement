package com.example.librarymanagement2;

public class Author {
    private String name;
    private String description;

    //Constructors
    public Author() {
        this.name = "";
        this.description = "";
    }

    public Author(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
