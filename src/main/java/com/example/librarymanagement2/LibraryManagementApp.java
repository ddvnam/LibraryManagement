package com.example.librarymanagement2;

public class LibraryManagementApp {
    public static void main(String[] args) {
        String username = "admin";
        String password = "admin";
        User user = User.login(username, password);
        Menu menu = new Menu();

        if(user != null) {
            if(user instanceof Admin) {
                menu.showMenu(user);
            }else if(user instanceof Borrower) {
                menu.showMenu(user);
            }
        }else {
            System.out.println("Invalid username or password");
        }
    }
}
