package com.example.librarymanagement2;

import java.util.Date;

public class BookItem extends Book{
    public enum BookFormat {
        HARDCOVER,
        PAPERBACK,
        AUDIO_BOOK,
        EBOOK,
        NEWSPAPER,
        MAGAZINE,
        JOURNAL
    }

    public enum BookStatus {
        AVAILABLE,
        RESERVED,
        LOANED,
        LOST
    }

    private String barcode;
    private boolean isReferenceOnly;
    private Date borrowed;
    private Date dueDate;
    private double price;
    private BookFormat format;
    private Date dateOfPurchase;
    private BookStatus status;

    public BookItem(String ISBN, String title, String subject, String publisher, String language, int numberOfPages, Author author, Date publicationDate) {
        super(ISBN, title, subject, publisher, language, numberOfPages, author, publicationDate);
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }

    public void setReferenceOnly(boolean referenceOnly) {
        isReferenceOnly = referenceOnly;
    }

    public Date getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Date borrowed) {
        this.borrowed = borrowed;
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

    public BookFormat getFormat() {
        return format;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
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
