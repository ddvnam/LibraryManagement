package com.example.librarymanagement2;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;

    public User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void findBook(String title) {
        System.out.println("Book found: " + title);
    }

    public static User login(String username, String password) {
        if(username == "admin") {
            return new Admin(1, "admin", "abc@gmail", password);
        }else if(username == "borrower") {
            return new Borrower(2, "borrower", "xyz@gmail", password);
        }else {
            return null;
        }
    }
}
