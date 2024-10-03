package com.example.librarymanagement2;

import java.util.List;

public class Library {
    private List<User> users;
    private List<Book> books;

    public Library(List<User> users, List<Book> books) {
        this.users = users;
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(Book book) {
        books.remove(book);
        System.out.println("Book removed: " + book.getTitle());
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getName());
    }

    public void removeUser(User user) {
        users.remove(user);
        System.out.println("User removed: " + user.getName());
    }
}
