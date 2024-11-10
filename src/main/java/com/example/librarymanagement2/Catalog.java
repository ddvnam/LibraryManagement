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
    private Map<String, List<Book>> bookPublishDates;
    private List<BookItem> bookItems;

    //Getters and Setters
    public Catalog() {
        this.creationDate = new Date();
        this.totalBooks = 0;
        this.bookTitles = new HashMap<>();
        this.bookAuthors = new HashMap<>();
        this.bookPublishDates = new HashMap<>();
        this.bookItems = new ArrayList<>();
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

    public List<BookItem> getBookItems() {
        return bookItems;
    }

    public void setBookItems(List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }

    public void addBookItem(BookItem bookItem) {
        bookItems.add(bookItem);
    }

    /**
     * Hàm này sẽ thêm BookItem vào Catalog
     * @param books
     * @return true nếu thêm sách thành công, ngược lại trả về false
     */
    public void loadBookItems(List<Book> books) {
        for (Book book : books) {
            BookItem bookItem = new BookItem(book.getISBN(), book.getTitle(), book.getPublisher(), book.getAuthor(), book.getPublicationDate());
            addBookItem(bookItem);
        }
    }
    /**
     * Hàm này sẽ thêm Book vào Catalog
     * @param books
     * @return true nếu thêm sách thành công, ngược lại trả về false
     */
    public void loadBooks(List<Book> books) {
        for (Book book : books) {
            updateCatalog(book);
        }
    }

    /**
     * Hàm này sẽ thêm Book vào Catalog theo Title , Author, PublishDate
     * @param newBook
     * @return true nếu thêm sách thành công, ngược lại trả về false
     */
    private boolean updateCatalog(Book newBook) {
        addBookToTitleMap(newBook);
        addBookToAuthorMap(newBook);
        addBookToPublishDateMap(newBook);
        return true;
    }

    /**
     * Hàm này sẽ thêm Book vào Catalog theo Title
     * @param newBook
     * @return true nếu thêm sách thành công, ngược lại trả về false
     */
    private void addBookToTitleMap(Book newBook) {
        String title = newBook.getTitle().trim();
        bookTitles.putIfAbsent(title, new ArrayList<>());
        bookTitles.get(title).add(newBook);
    }

    /**
     * Hàm này sẽ thêm Book vào Catalog theo Author
     * @param newBook
     * @return true nếu thêm sách thành công, ngược lại trả về false
     */
    private void addBookToAuthorMap(Book newBook) {
        String authorName = newBook.getAuthor().trim();
        bookAuthors.putIfAbsent(authorName, new ArrayList<>());
        bookAuthors.get(authorName).add(newBook);
    }

    /**
     * Hàm này sẽ thêm Book vào Catalog theo PublishDate
     * @param newBook
     * @return true nếu thêm sách thành công, ngược lại trả về false
     */
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

    /**
     * Hàm này sẽ trả về BookItem dựa trên Book
     * @param book
     * @return BookItem
     */
    public BookItem getBookItem(Book book) {
        for (BookItem bookItem : bookItems) {
            if (bookItem.getTitle().equals(book.getTitle())) {
                return bookItem;
            }
        }
        return null;
    }

    /**
     * Hàm này sẽ trả về BookItem dựa trên BookItem
     * Tìm kiếm BookItem trong Catalog dựa trên BookItem
     * @param bookItem
     * @return BookItem
     */
    public BookItem getBookItem(BookItem bookItem) {
        for (BookItem item : bookItems) {
            if (item.getTitle().equals(bookItem.getTitle())) {
                return item;
            }
        }
        return null;
    }
}
