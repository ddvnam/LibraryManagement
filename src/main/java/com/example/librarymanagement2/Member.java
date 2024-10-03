package com.example.librarymanagement2;

import java.util.Date;

public class Member extends Account{
    private Date dateOfMembership;
    private int totalBooksCheckedout;

    public int getTotalBooksCheckedout() {
        return totalBooksCheckedout;
    }
}
