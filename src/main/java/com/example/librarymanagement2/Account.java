package com.example.librarymanagement2;

public class Account {
    public enum AccountStatus {
        ACTIVE, CANCELED, BLACKLISTED, BLOCKED, NONE
    }
    private String username; // thay the cho id
    private String password;
    private AccountStatus status;
    private String role;
    private Person person;

    //Constructors
    public Account() {
        this.username = "";
        this.password = "";
        this.status = AccountStatus.NONE;
        this.person = new Person();
    }

    public Account(String username, String password, AccountStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public Account(String username, String password, AccountStatus status, Person person) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.person = person;
    }

    //Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public boolean validateLogin(String username, String password) {
        if(this.status == AccountStatus.CANCELED) {
            System.out.println("This account has been canceled. Please register a new account.");
            return false;
        }
        return this.username.equals(username) && this.password.equals(password);
    }
    public boolean resetPassword() {
        return true;
    }
}
