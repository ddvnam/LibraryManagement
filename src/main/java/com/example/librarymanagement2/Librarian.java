package com.example.librarymanagement2;

import java.util.Date;
import java.util.Objects;

public class Librarian extends Account{

    public Librarian(String username, String password, String email) {
        super(username, password, email , AccountStatus.ACTIVE);
        this.setRole("librarian");
    }

    public void addBookItem(String ISBN, String title, String publisher,  String author, String publicationDate) {
        System.out.println("Book added successfully.");
    }

    public void editBookItem(BookItem bookItem, String newTitle, String newAuthor, String newIsbn) {
        bookItem.setTitle(newTitle);
        bookItem.setAuthor(newAuthor);
        bookItem.setISBN(newIsbn);
        System.out.println("Book edited successfully.");
    }

    public void removeBookItem(BookItem bookItem) {
        System.out.println("Book removed successfully.");
    }

    public static void blockMember(Member member) {
        member.setStatus(AccountStatus.BLOCKED);
        System.out.println("Member blocked : " + member.getUsername());
        //Gui thong bao ve email
        if (!Objects.equals(member.getEmail(),"")) {
            String content = "Bạn đã bị khóa tài khoản thư viện!";
            String subject = "THÔNG BÁO KHÓA TÀI KHOẢN";
            member.sendEmailNotificationMember(content,subject);
        }
    }

    public static void unblockMember(Member member) {
        member.setStatus(AccountStatus.ACTIVE);
        System.out.println("Member unblocked : " + member.getUsername());
        if (!Objects.equals(member.getEmail(),"")) {
            String content = "Tài khoản của bạn đã được mở!";
            String subject = "THÔNG BÁO MỞ KHÓA TÀI KHOẢN";
            member.sendEmailNotificationMember(content,subject);
        }
    }

    public static void cancelMembership(Member member) {
        member.setStatus(AccountStatus.CANCELED);
        System.out.println("Membership canceled : " + member.getUsername());
    }

}
