package com.example.librarymanagement2;

import java.util.Date;

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
}
