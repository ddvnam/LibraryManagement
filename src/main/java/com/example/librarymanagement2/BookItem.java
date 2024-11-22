package com.example.librarymanagement2;

import java.util.Date;
import java.util.Random;

public class BookItem extends Book{

    public enum BookStatus {
        AVAILABLE,
        RESERVED,
        UNAVAILABLE,
    }

    private double price;
    private BookStatus status;
    private int numOfCopies;

    public BookItem(String ISBN, String title, String publisher, String author, String publicationDate, double price, int numOfCopies, String imageUrl) {
        super(ISBN, title, publisher, author, publicationDate, imageUrl);
        this.price = price;
        this.numOfCopies = numOfCopies;
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

    public int getNumOfCopies() {
        return numOfCopies;
    }

    public void setNumOfCopies(int numOfCopies) {
        this.numOfCopies = numOfCopies;
    }

    /**
     * Hàm này sẽ kiểm tra xem sách có thể mượn được không
     * @return true nếu sách có thể mượn, ngược lại trả về false
     */
    public boolean checkout() {
        if(this.numOfCopies > 0) {
            this.numOfCopies--;
            if(this.numOfCopies == 0) {
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
        if(this.numOfCopies >= 0) {
            this.numOfCopies++;
            if(this.numOfCopies > 0) {
                this.status = BookStatus.AVAILABLE;
            }
            return true;
        }
        return false;
    }
}
