package com.example.librarymanagement2;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    // Method to display the main menu
    public void showMainMenu() {
        System.out.println("Welcome to the Library Management System");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
    }

    // Method to display the admin menu
    public void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Block Account");
            System.out.println("2. Unblock Account");
            System.out.println("3. Cancel Account");
            System.out.println("4. Check Account Status");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = getUserInput();
            scanner.nextLine();  // consume the newline
            switch(choice) {
                case 1:
                    System.out.println("Block Account");
                    System.out.println("Enter username: ");
                    String username = getUserStringInput();
                    // Find the account in the accounts list
                    Account foundAccount = findMemberByUsername(username);
                    if(foundAccount != null && foundAccount instanceof Member) {
                        Librarian.blockMember((Member) foundAccount);
                        System.out.println("Account " + username + " has been blocked.");
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;
                case 2:
                    System.out.println("Unblock Account");
                    System.out.println("Enter username: ");
                    username = getUserStringInput();
                    foundAccount = findMemberByUsername(username);
                    if(foundAccount != null && foundAccount instanceof Member) {
                        if(foundAccount.getStatus() == Account.AccountStatus.BLOCKED) {
                            Librarian.unblockMember((Member) foundAccount);
                            System.out.println("Account " + username + " has been unblocked.");
                        } else {
                            System.out.println("Account " + username + " is not blocked.");
                        }
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;
                case 3:
                    System.out.println("Cancel Account");
                    break;
                case 4:
                    System.out.println("Check Account Status");
                    for(Account account : LibraryApp.accounts) {
                        System.out.println(account.getUsername() + " : " + account.getStatus());
                    }
                    break;
                case 5:
                    System.out.println("Are you sure you want to exit? (Y/N)");
                    char exitChoice = getUserStringInput().trim().charAt(0);
                    if(exitChoice == 'Y' || exitChoice == 'y') {
                        System.out.println("Exiting...");
                        System.exit(0);
                    } else {
                        showAdminMenu();
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // Helper method to find member by username
    private Account findMemberByUsername(String username) {
        for(Account account : LibraryApp.accounts) {
            if(account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public void showMemberMenu() {
        while (true) {
            System.out.println("\nMember Menu:");
            System.out.println("1. Search Books");
            System.out.println("2. Check Out Book");
            System.out.println("3. Return Book");
            System.out.println("4. Renew Book");
            System.out.println("5. Pay Fine");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = getUserInput();
            scanner.nextLine();
            switch(choice) {
                case 1:
                    System.out.println("Search Books");
                    break;
                case 2:
                    System.out.println("Check Out Book");
                    break;
                case 3:
                    System.out.println("Return Book");
                    break;
                case 4:
                    System.out.println("Renew Book");
                    break;
                case 5:
                    System.out.println("Pay Fine");
                    break;
                case 6:
                    System.out.println("Are you sure you want to exit? (Y/N)");
                    char exitChoice = getUserStringInput().trim().charAt(0);
                    if (exitChoice == 'Y' || exitChoice == 'y') {
                        System.out.println("Exiting...");
                        System.exit(0);
                    } else {
                        showMemberMenu();
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    // Method to read user input
    public int getUserInput() {
        return scanner.nextInt();
    }

    // Method to read user input as a string
    public String getUserStringInput() {
        return scanner.nextLine();
    }

    // Closing the scanner to prevent resource leaks
    public void close() {
        scanner.close();
    }
}
