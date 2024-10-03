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
    private Date publicationDate;
    private BookStatus status;

    public boolean checkout() {
        // Implement checkout logic
        return true;
    }
}
