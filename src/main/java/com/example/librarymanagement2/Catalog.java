package com.example.librarymanagement2;

import java.util.*;

public class Catalog implements Search {
    private Date creationDate;
    private int totalBooks;
    private Map<String, List<Book>> bookTitles;
    private Map<String, List<Book>> bookAuthors;
    private Map<String, List<Book>> bookSubjects;
    private Map<Date, List<Book>> bookPublishDates;

    //Getters and Setters
    public Catalog() {
        this.creationDate = new Date();
        this.totalBooks = 0;
        this.bookTitles = new HashMap<>();
        this.bookAuthors = new HashMap<>();
        this.bookSubjects = new HashMap<>();
        this.bookPublishDates = new HashMap<>();
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public boolean updateCatalog(Book newBook) {
        addBookToTitleMap(newBook);
        addBookToAuthorMap(newBook);
        addBookToSubjectMap(newBook);
        addBookToPublishDateMap(newBook);
        return true;
    }

    private void addBookToTitleMap(Book newBook) {
        String title = newBook.getTitle();
        bookTitles.putIfAbsent(title, new ArrayList<>());
        bookTitles.get(title).add(newBook);
    }

    private void addBookToAuthorMap(Book newBook) {
        String authorName = newBook.getAuthor().getName();
        bookAuthors.putIfAbsent(authorName, new ArrayList<>());
        bookAuthors.get(authorName).add(newBook);
    }

    private void addBookToSubjectMap(Book newBook) {
        String subject = newBook.getSubject();
        bookSubjects.putIfAbsent(subject, new ArrayList<>());
        bookSubjects.get(subject).add(newBook);
    }

    private void addBookToPublishDateMap(Book newBook) {
        Date publishDate = newBook.getPublicationDate();
        bookPublishDates.putIfAbsent(publishDate, new ArrayList<>());
        bookPublishDates.get(publishDate).add(newBook);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return bookTitles.get(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return bookAuthors.get(author);
    }

    @Override
    public List<Book> searchBySubject(String subject) {
        return bookSubjects.get(subject);
    }

    @Override
    public List<Book> searchByPubDate(Date publishDate) {
        return bookPublishDates.get(publishDate);
    }
}
