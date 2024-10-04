package com.example.librarymanagement2;

public class Librarian extends Account{

    public Librarian(String username, String password) {
        super(username, password, AccountStatus.ACTIVE);
        this.setRole("librarian");
    }

    public void addBookItem(String ISBN, String title, String subject, String publisher, String language, int numberOfPages) {
        BookItem bookItem = new BookItem(ISBN, title, subject, publisher, language, numberOfPages);
        LibraryApp.bookItems.add(bookItem);
        System.out.println("Book added : " + bookItem.getTitle() + " by " + bookItem.getAuthor().getName());
    }

    public void editBookItem(BookItem bookItem, String newTitle, Author newAuthor, String newIsbn) {
        bookItem.setTitle(newTitle);
        bookItem.setAuthor(newAuthor);
        bookItem.setISBN(newIsbn);
        System.out.println("Book edited successfully.");
    }

    public void removeBookItem(BookItem bookItem) {
        LibraryApp.bookItems.remove(bookItem);
        System.out.println("Book removed successfully.");
    }

    public static void blockMember(Member member) {
        member.setStatus(AccountStatus.BLOCKED);
        System.out.println("Member blocked : " + member.getUsername());
    }

    public static void unblockMember(Member member) {
        member.setStatus(AccountStatus.ACTIVE);
        System.out.println("Member unblocked : " + member.getUsername());
    }

    public static void cancelMembership(Member member) {
        member.setStatus(AccountStatus.CANCELED);
        System.out.println("Membership canceled : " + member.getUsername());
    }
}
