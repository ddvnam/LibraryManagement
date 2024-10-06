package com.example.librarymanagement2;

import java.util.Date;
import java.util.List;

public class Book {
    private String ISBN;
    private String title;
    private String publisher;
    private Author author;
    private String publicationDate;//only year

    public Book(String isbn, String title, String publisher, Author author, String publicationDate) {
        this.ISBN = isbn;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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

    public void getInformation() {
        System.out.println("ISBN: " + ISBN);
        System.out.println("Title: " + title);
        System.out.println("Publisher: " + publisher);
        System.out.println("Author: " + author.getName());
        System.out.println("Publication Date: " + publicationDate);
    }
}
