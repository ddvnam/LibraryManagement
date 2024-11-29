package com.example.librarymanagement2;

import java.time.LocalDate;

public class BookLoan {
    private int accountId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public BookLoan(int accountId, int bookId, LocalDate issueDate, LocalDate dueDate) {
        this.accountId = accountId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
