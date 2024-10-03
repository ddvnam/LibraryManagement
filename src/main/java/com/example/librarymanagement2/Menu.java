package com.example.librarymanagement2;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(User user) {
        while(true) {
            if(user instanceof Admin) {
                showAdminMenu((Admin) user);
            }else if(user instanceof Borrower) {
                showBorrowerMenu((Borrower) user);
            }
        }
    }

    private void showAdminMenu(Admin admin) {
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Update book");
        System.out.println("4. Manage borrowings");
        System.out.println("5. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                String title = scanner.next();
                Book book = new Book(title);
                admin.add(book);
                break;
            case 2:
                System.out.println("Enter book title:");
                title = scanner.next();
                book = new Book(title);
                admin.remove(book);
                break;
            case 3:
                System.out.println("Enter book title:");
                title = scanner.next();
                book = new Book(title);
                admin.update(book);
                break;
            case 4:
                admin.manageBorrowings();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void showBorrowerMenu(Borrower borrower) {
        System.out.println("1. Borrow book");
        System.out.println("2. Return book");
        System.out.println("3. View borrowed books");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter book title:");
                String title = scanner.next();
                Book book = new Book(title);
                borrower.borrowBook(book);
                break;
            case 2:
                System.out.println("Enter book title:");
                title = scanner.next();
                book = new Book(title);
                borrower.returnBook(book);
                break;
            case 3:
                borrower.viewBorrowedBooks();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}
