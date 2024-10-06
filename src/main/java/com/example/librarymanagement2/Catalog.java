package com.example.librarymanagement2;

import java.util.*;

/**
 * Catalog là class chứa thông tin cuốn sách và cung cấp các phương thức tìm kiếm sách
 * Catalog quản lí BookItem
 */
public class Catalog implements Search {
    private Date creationDate;
    private int totalBooks;
    private Map<String, List<Book>> bookTitles;
    private Map<String, List<Book>> bookAuthors;
    private Map<String, List<Book>> bookSubjects;
    private Map<String, List<Book>> bookPublishDates;

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

    public void loadData(List<Book> books) {
        for (Book book : books) {
            updateCatalog(book);
        }
    }
    private boolean updateCatalog(Book newBook) {
        addBookToTitleMap(newBook);
        addBookToAuthorMap(newBook);
        addBookToPublishDateMap(newBook);
        return true;
    }

    private void addBookToTitleMap(Book newBook) {
        String title = newBook.getTitle().trim();
        bookTitles.putIfAbsent(title, new ArrayList<>());
        bookTitles.get(title).add(newBook);
    }

    private void addBookToAuthorMap(Book newBook) {
        String authorName = newBook.getAuthor().getName().trim();
        bookAuthors.putIfAbsent(authorName, new ArrayList<>());
        bookAuthors.get(authorName).add(newBook);
    }

    private void addBookToPublishDateMap(Book newBook) {
        String publishDate = newBook.getPublicationDate();
        bookPublishDates.putIfAbsent(publishDate, new ArrayList<>());
        bookPublishDates.get(publishDate).add(newBook);
    }

    /**
     * Tìm kiếm sách theo tiêu đề
     * @param title
     * @return  List<Book> chứa các cuốn sách có tiêu đề trùng với title
     */
    @Override
    public List<Book> searchByTitle(String title) {
        return bookTitles.get(title.trim());
    }

    /**
     * Tìm kiếm sách theo tác giả
     * @param author
     * @return List<Book> chứa các cuốn sách có tác giả trùng với author
     */
    @Override
    public List<Book> searchByAuthor(String author) {
        return bookAuthors.get(author);
    }

    /**
     * Tìm kiếm sách theo ngày xuất bản
     * @param publishDate
     * @return List<Book> chứa các cuốn sách có ngày xuất bản trùng với publishDate
     */
    @Override
    public List<Book> searchByPubDate(String publishDate) {
        return bookPublishDates.get(publishDate);
    }
}
