package com.example.librarymanagement2;

import java.util.Date;
import java.util.List;

public class Book {
    private String ISBN;
    private String title;
    private String publisher;
    private String author;
    private String publicationDate;//only year
    private String imageUrl;
    private String description;

    public Book(String isbn, String title, String publisher, String author, String publicationDate, String description) {
        this.ISBN = isbn;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.publicationDate = publicationDate;
        this.description = description;
    }

    public Book(String isbn, String title, String publisher, String author, String publicationDate, String imageUrl, String description) {
        this.ISBN = isbn;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.publicationDate = publicationDate;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Book(String isbn, String title, String publisher, String author, String publicationDate) {
        this.ISBN = isbn;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public Book(String bookName, String author, String year) {
        this.title = bookName;
        this.author = author;
        this.publicationDate = year;
    }

    public Book(String isbn,String bookName, String author, String year) {
        this.ISBN = isbn;
        this.title = bookName;
        this.author = author;
        this.publicationDate = year;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}