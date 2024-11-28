package com.example.librarymanagement2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId; // Unique ID for the transaction
    private String memberId;      // ID of the member making the transaction
    private String bookId;        // ID of the book involved in the transaction
    private String transactionType; // Type of transaction: "BORROW" or "RETURN"
    private LocalDateTime transactionDate; // Date and time of the transaction
    private LocalDateTime dueDate; // Due date for borrowed books
    private boolean isOverdue; // Indicates if the transaction is overdue
    private String status;

    // Constructor
    public Transaction(String transactionId, String memberId, String bookId, String transactionType, LocalDateTime transactionDate, LocalDateTime dueDate) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.isOverdue = false;
    }


    public Transaction(String transactionId, String bookId, String transactionType, LocalDateTime transactionDate, LocalDateTime dueDate, String status) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.status = status;
    }


    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to display transaction details
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}