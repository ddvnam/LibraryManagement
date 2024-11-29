package com.example.librarymanagement2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private int accountId;
    private int bookId;
    private LocalDate transactionDate;
    private double total;
    private double remain;

    public Transaction(int accountId, int bookId, LocalDate transactionDate, double total, double remain) {
        this.accountId = accountId;
        this.bookId = bookId;
        this.transactionDate = transactionDate;
        this.total = total;
        this.remain = remain;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public void createTransaction() {
        // Code to create a transaction
        Database db = new Database();
        db.connectToDatabase();
        String query = "INSERT INTO transaction (account_id, book_id, transaction_date, total, remain) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
            // Set the parameters
            stmt.setInt(1, accountId);
            stmt.setInt(2, bookId);
            stmt.setObject(3, transactionDate);
            stmt.setDouble(4, total);
            stmt.setDouble(5, remain);

            // Execute the query
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Error creating transaction", e);
        } finally {
            db.disConnectDatabase();
        }
    }

}