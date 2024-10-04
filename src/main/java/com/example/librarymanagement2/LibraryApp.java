package com.example.librarymanagement2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    public static List<BookItem> bookItems = new ArrayList<>();
    public static List<Account> accounts = new ArrayList<>();
    private static Menu menu = new Menu();
    private static Account currentAccount;

    public static void main(String[] args) {
        accounts.add(new Librarian("lib1", "123"));
        accounts.add(new Librarian("lib2", "123"));
        accounts.add(new Member("member1", "123"));
        accounts.add(new Member("member2", "123"));

        while(true) {
            menu.showMainMenu();
            int choice = menu.getUserInput();
            if(choice == 1) {
                if(Login()) {
                    System.out.println("Login successful");
                    if(currentAccount.getRole() == "librarian") {
                        menu.showAdminMenu();
                    }
                    if(currentAccount.getRole() == "member") {
                        menu.showMemberMenu();
                    }

                } else {
                    System.out.println("Login failed");
                }
            } else if(choice == 2) {
                break;
            }
        }
    }

    public static boolean Login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String user = scanner.nextLine();
        System.out.println("Enter password: ");
        String pass = scanner.nextLine();

        for( Account account : accounts) {
            if(account.validateLogin(user, pass)) {
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    public static boolean Register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String user = scanner.nextLine();
        System.out.println("Enter password: ");
        String pass = scanner.nextLine();
        System.out.println("Enter password again: ");
        String pass2 = scanner.nextLine();
        if(pass.equals(pass2)) {
            accounts.add(new Account(user, pass, Account.AccountStatus.ACTIVE));
            return true;
        }
        return false;
    }

}
