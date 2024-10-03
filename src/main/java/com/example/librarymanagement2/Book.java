package com.example.librarymanagement2;

import java.util.List;

public class Book {
    private String ISBN;
    private String title;
    private String subject;
    private String publisher;
    private String language;
    private int numberOfPages;
    private List<Author> authors;

    public String getTitle() {
        return title;
    }
}
