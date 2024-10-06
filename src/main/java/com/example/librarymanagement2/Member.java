package com.example.librarymanagement2;

import java.util.*;

public class Member extends Account{
    private Date dateOfMembership;
    private int totalBooksCheckedout;

    public Member(String username, String password) {
        super(username, password, AccountStatus.ACTIVE);
        this.setRole("member");
    }

    public Member(String username) {
        this.setUsername(username);
        this.setRole("member");
    }
    public int getTotalBooksCheckedout() {
        return totalBooksCheckedout;
    }


    public void searchBooks(Catalog catalog) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select search option:");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Publication Date");
        int choice = scanner.nextInt();
        scanner.nextLine();
        List<Book> results = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                results = catalog.searchByTitle(title);
                break;
            case 2:
                System.out.print("Enter author name: ");
                String author = scanner.nextLine();
                results = catalog.searchByAuthor(author);
                break;
            case 3:
                System.out.print("Enter year: ");
                String year = scanner.nextLine();
                results = catalog.searchByPubDate(year);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        // Display search results
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Search Results:");
            for (Book book : results) {
                System.out.println(book.getTitle() + " by " + String.join(", ", book.getAuthor().getName()));
            }
        }
    }
}
