package com.example.librarymanagement2;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Book {
    private Random random = new Random();
    private double price;
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
        this.price = random.nextDouble();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *  Hàm này sẽ hiển thị thông tin của sách
     */
    public void getInformation() {
        System.out.println("ISBN: " + ISBN);
        System.out.println("Title: " + title);
        System.out.println("Publisher: " + publisher);
        System.out.println("Author: " + author.getName());
        System.out.println("Publication Date: " + publicationDate);
        System.out.println("Price: " + price);
    }
}
