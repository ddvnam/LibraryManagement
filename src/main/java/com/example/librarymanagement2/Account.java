package com.example.librarymanagement2;

public class Account {
    public enum AcountStatus {
        ACTIVE, CLOSED, CANCELED, BLACKLISTED, NONE
    }
    private String username; // thay the cho id
    private String password;
    private AcountStatus status;
    private Person person;

    //Constructors
    public Account() {
        this.username = "";
        this.password = "";
        this.status = AcountStatus.NONE;
        this.person = new Person();
    }

    public Account(String username, String password, AcountStatus status, Person person) {
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

    public AcountStatus getStatus() {
        return status;
    }

    public void setStatus(AcountStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
