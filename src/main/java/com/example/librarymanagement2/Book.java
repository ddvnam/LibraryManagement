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

    public Book(String isbn, String title, String publisher, String author, String publicationDate, String imageUrl) {
        this.ISBN = isbn;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.publicationDate = publicationDate;
        this.imageUrl = imageUrl;
    }

    public Book(String isbn, String title, String publisher, String author, String publicationDate) {
        this.ISBN = isbn;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.publicationDate = publicationDate;
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
    /**
     *  Hàm này sẽ hiển thị thông tin của sách
     */
    public void getInformation() {
        System.out.println("ISBN: " + ISBN);
        System.out.println("Title: " + title);
        System.out.println("Publisher: " + publisher);
        System.out.println("Author: " + author);
        System.out.println("Publication Date: " + publicationDate);
    }
}
