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

    public static boolean Login(String username, String password) {
        for( Account account : accounts) {
            if(account.validateLogin(username, password)) {
                return true;
            }
        }
        return false;
    }

    /* Da thay doi */
    public static boolean Register(String username, String password, String email) {
        //check if username already exists
        if(accounts.stream().anyMatch(account -> account.getUsername().equals(username))) {
            return false;
        } else {
            Account account = new Account(username, password, email, "member");
            accounts.add(account);
            return true;
        }
    }

}
