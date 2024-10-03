package com.example.librarymanagement2;

public class Admin extends User{
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, "admin");
    }

    public void add(Book book) {
        System.out.println("Book added: " + book.getTitle());
    }

    public void remove(Book book) {
        System.out.println("Book removed: " + book.getTitle());
    }

    public void update(Book book) {
        System.out.println("Book updated: " + book.getTitle());
    }

    public void manageBorrowings() {
        System.out.println("Borrowings managed");
    }
}
