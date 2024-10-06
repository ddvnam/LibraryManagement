package com.example.librarymanagement2;

import java.util.Date;

public class BookItem extends Book{

    public enum BookStatus {
        AVAILABLE,
        RESERVED,
        UNAVAILABLE,
    }

    private String barcode;
    private Date dueDate;
    private double price;
    private Date dateOfPurchase;
    private BookStatus status;

    public BookItem(String ISBN, String title, String publisher, Author author, String publicationDate) {
        super(ISBN, title, publisher, author, publicationDate);
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public boolean checkout() {
        // Implement checkout logic
        return true;
    }
}
