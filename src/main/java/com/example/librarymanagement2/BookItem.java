package com.example.librarymanagement2;

import java.util.Date;
import java.util.Random;

public class BookItem extends Book{

    public BookItem(String bookName, String author, String year, double price, String imageUrl) {
        super(bookName, author, year, imageUrl);
        this.price = price;
        this.status = BookStatus.AVAILABLE;
        this.Copies = 1;
    }

    public BookItem(String isbn, String bookName, String author, String year, double price) {
        super(isbn, bookName, author ,year);
        this.price = price;
    }

    public enum BookStatus {
        AVAILABLE,
        RESERVED,
        UNAVAILABLE,
    }

    private double price;
    private BookStatus status;
    private int Copies;
    public BookItem(String bookName, String author, String year, double price)
    {
        super(bookName, author, year);
        this.price = price;
    }

    public BookItem(String ISBN, String title, String publisher, String author, String publicationDate, double price, int Copies, String description) {
        super(ISBN, title, publisher, author, publicationDate, description);
        this.price = price;
        this.Copies = Copies;
        this.status = BookStatus.AVAILABLE;
    }

    public BookItem(String ISBN, String title, String publisher, String author, String publicationDate, double price, int copies, String imageUrl, String description) {
        super(ISBN, title, publisher, author, publicationDate, imageUrl, description);
        this.price = price;
        this.Copies = copies;
        this.status = BookStatus.AVAILABLE;
    }


    public BookItem(String ISBN, String title, String publisher, String author, String publicationDate,int Copies, double price) {
        super(ISBN, title, publisher, author, publicationDate);
        this.price = price;
        this.Copies = Copies;
        this.status = BookStatus.AVAILABLE;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public int getCopies() {
        return Copies;
    }

    public void setCopies(int numOfCopies) {
        this.Copies = numOfCopies;
    }

    /**
     * Hàm này sẽ kiểm tra xem sách có thể mượn được không
     * @return true nếu sách có thể mượn, ngược lại trả về false
     */
    public boolean checkout() {
        if(this.Copies > 0) {
            this.Copies--;
            if(this.Copies == 0) {
                this.status = BookStatus.UNAVAILABLE;
            }
            return true;
        }
        return false;
    }

    /**
     * Hàm này sẽ kiểm tra xem sách có thể trả được không
     * @return true nếu sách có thể trả, ngược lại trả về false
     */
    public boolean checkin() {
        if(this.Copies >= 0) {
            this.Copies++;
            if(this.Copies > 0) {
                this.status = BookStatus.AVAILABLE;
            }
            return true;
        }
        return false;
    }
}