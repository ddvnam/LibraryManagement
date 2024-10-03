package com.example.librarymanagement2;

import java.util.ArrayList;
import java.util.List;

public class Borrower extends User{
    private List<Book> borrowedBooks;

    public Borrower(int id, String name, String email, String password) {
        super(id, name, email, password, "borrower");
        borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        System.out.println("Book borrowed: " + book.getTitle());
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        System.out.println("Book returned: " + book.getTitle());
    }

    public void viewBorrowedBooks() {
        System.out.println("Borrowed books:");
        for (Book book : borrowedBooks) {
            System.out.println(book.getTitle());
        }
    }
}
