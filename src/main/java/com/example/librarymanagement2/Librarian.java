package com.example.librarymanagement2;

public class Librarian extends Account{

    public Librarian(String username, String password) {
        super(username, password, AccountStatus.ACTIVE);
        this.setRole("librarian");
    }

    public boolean addBookItem( BookItem bookItem) {
        //code to add book item
        return true;
    }

    public boolean blockMember(Member member) {
        //code for blocking member
        return true;
    }

    public boolean unBlockMember(Member member) {
        //code for unblocking member
        return true;
    }
}
