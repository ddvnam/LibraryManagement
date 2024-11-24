package com.example.librarymanagement2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import com.example.librarymanagement2.LibraryApp.*;

import static com.example.librarymanagement2.LibraryApp.db;

public class Librarian extends Account{

    public Librarian(String username, String password, String email) {
        super(username, password, email , AccountStatus.ACTIVE);
        this.setRole("librarian");
    }

    public void addBookItem(BookItem bookItem) {
        String insertBookQuery = "INSERT INTO book (isbn, title, author, publication_date, publisher) VALUES (?, ?, ?, ?, ?)";
        String insertBookItemQuery = "INSERT INTO book_item (book_id, price, no_of_copy) VALUES (?, ?, ?)";
        String insertBookImageQuery = "INSERT INTO book_image (book_id, image_url) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement psBook = null;
        PreparedStatement psBookItem = null;
        PreparedStatement psBookImage = null;
        ResultSet generatedKeys = null;

        try {
            conn = db.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert into the book table
            psBook = conn.prepareStatement(insertBookQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            psBook.setString(1, bookItem.getISBN());
            psBook.setString(2, bookItem.getTitle());
            psBook.setString(3, bookItem.getAuthor());
            psBook.setString(4, bookItem.getPublicationDate());
            psBook.setString(5, bookItem.getPublisher());
            psBook.executeUpdate();

            // Get the generated book_id
            generatedKeys = psBook.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new Exception("Failed to retrieve generated book_id.");
            }
            int bookId = generatedKeys.getInt(1);

            // Insert into the book_item table
            psBookItem = conn.prepareStatement(insertBookItemQuery);
            psBookItem.setInt(1, bookId);
            psBookItem.setDouble(2, bookItem.getPrice());
            psBookItem.setInt(3, bookItem.getCopies());
            psBookItem.executeUpdate();

            // Insert into the book_image table
            psBookImage = conn.prepareStatement(insertBookImageQuery);
            psBookImage.setInt(1, bookId);
            psBookImage.setString(2, bookItem.getImageUrl());
            psBookImage.executeUpdate();

            conn.commit();
            System.out.println("Book added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                if (psBook != null) psBook.close();
                if (psBookItem != null) psBookItem.close();
                if (psBookImage != null) psBookImage.close();
                if (generatedKeys != null) generatedKeys.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
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
