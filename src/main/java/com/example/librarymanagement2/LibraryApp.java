package com.example.librarymanagement2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    public static final String FILE_NAME = "D:\\Learning\\Java\\LibraryManagement\\src\\main\\java\\Database\\books.txt";
    public static List<Book> books = new ArrayList<>();
    public static List<BookItem> bookItems = new ArrayList<>();
    public static Database database = new Database();
    public static Catalog catalog = new Catalog();
    public static List<Account> accounts = new ArrayList<>();
    private static Menu menu = new Menu();
    private static Account currentAccount;

    public static void main(String[] args) throws IOException {
        // Testing Account
        accounts.add(new Librarian("lib1", "123"));
        accounts.add(new Librarian("lib2", "123"));
        accounts.add(new Member("member1", "123"));
        accounts.add(new Member("member2", "123"));

        //Load data
        database.loadData(books, FILE_NAME);
        catalog.loadData(books);

        List<Book> temp = catalog.searchByTitle("Classical Mythology");
        for (Book book : temp) {
            System.out.println(book.getTitle());
        }
        while(true) {
            menu.showMainMenu();
            int choice = menu.getUserInput();
            if(choice == 1) {
                if(Login()) {
                    System.out.println("Login successful");
                    if(currentAccount instanceof Librarian) {
                        menu.showAdminMenu();
                    }
                    if(currentAccount instanceof Member) {
                        menu.showMemberMenu((Member) currentAccount, catalog);
                    }

                } else {
                    System.out.println("Login failed");
                }
            } else if(choice == 2) {
                if(Register()) {
                    System.out.println("Register successful");
                } else {
                    System.out.println("Register failed");
                }
            } else if(choice == 3) {
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
        String user = scanner.nextLine().trim();
        System.out.println("Enter password: ");
        String pass = scanner.nextLine();
        System.out.println("Enter password again: ");
        String pass2 = scanner.nextLine();
        if (pass.equals(pass2)) {
            Account newAccount = new Account(user, pass, Account.AccountStatus.ACTIVE);
            newAccount.setRole("member");
            accounts.add(newAccount);
            return true;
        }
        return false;
    }

}
