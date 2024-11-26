package com.example.librarymanagement2;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {
    private List<Transaction> transactionHistory;

    public TransactionService() {
        this.transactionHistory = new ArrayList<>();
    }

    // Method to add a transaction
    public void addTransaction(String memberId, String bookId, String transactionType) {
        String transactionId = generateTransactionId();
        LocalDateTime transactionDate = LocalDateTime.now(); // Current date and time
        LocalDateTime dueDate = null;
        if (transactionType.equals("BORROW")) {
            dueDate = transactionDate.plusWeeks(2); // Setting due date 2 weeks from now for borrow transactions
        }
        Transaction transaction = new Transaction(transactionId, memberId, bookId, transactionType, transactionDate, dueDate);
        transactionHistory.add(transaction);
        System.out.println("Transaction recorded: " + transaction);
    }

    // Method to generate unique transaction ID using UUID
    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    // Method to display all transactions
    public void displayAllTransactions() {
        System.out.println("Transaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("There is no transaction in library");
        }
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    // Method to check and update overdue transactions
    public void updateOverdueTransactions() {
        LocalDateTime now = LocalDateTime.now();
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionType().equals("BORROW") && transaction.getDueDate() != null && now.isAfter(transaction.getDueDate())) {
                transaction.setOverdue(true);
            }
        }
    }

    // Method to display overdue transactions
    public void displayOverdueTransactions() {
        System.out.println("Overdue Transactions:");
        List<Transaction> overdueTransactions = transactionHistory.stream()
                .filter(Transaction::isOverdue)
                .collect(Collectors.toList());
        for (Transaction transaction : overdueTransactions) {
            System.out.println(transaction);
        }
    }

    // Method to search transactions by member ID
    public List<Transaction> searchTransactionsByMember(String memberId) {
        return transactionHistory.stream()
                .filter(transaction -> transaction.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }

    public void displayTransactionsByMemberId(String memberId) {
        List<Transaction> transactions = searchTransactionsByMember(memberId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for Member ID: " + memberId);
        } else {
            System.out.println("Transactions for Member ID: " + memberId);
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public void applyLateFees() {
        LocalDateTime now = LocalDateTime.now();
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionType().equals("BORROW") && transaction.getDueDate() != null && now.isAfter(transaction.getDueDate())) {
                long daysOverdue = java.time.Duration.between(transaction.getDueDate(), now).toDays();
                if (daysOverdue > 0) {
                    transaction.setOverdue(true);
                    double lateFee = daysOverdue * 1.0; // Ví dụ: Phí phạt là 1 đơn vị tiền tệ cho mỗi ngày trễ.

                    // Tìm thành viên và cập nhật phí phạt
                    Account memberAccount = LibraryApp.getAccountById(transaction.getMemberId());
                    if (memberAccount != null && memberAccount instanceof Member) {
                        Member member = (Member) memberAccount;
                        member.setBalance(member.getBalance() - lateFee);
                        System.out.println("Late fee of " + lateFee + " applied to member: " + member.getUsername());

                        // Gửi thông báo cổng thông tin (Portal Notification)
                        PortalNotification portalNotification = new PortalNotification(
                                1, new Date(),
                                "Bạn đã bị phạt " + lateFee + " do trễ hạn trả sách cho cuốn sách có ID: " + transaction.getBookId()
                        );
                        member.addPortalNT(portalNotification);

                        // Gửi thông báo email nếu có email được cung cấp
                        if (!member.getEmail().isEmpty()) {
                            String content = "Bạn đã bị phạt " + lateFee + " do trễ hạn trả sách. Vui lòng hoàn thành thanh toán để tiếp tục sử dụng dịch vụ thư viện.";
                            String subject = "THÔNG BÁO PHẠT TRỄ HẠN";
                            member.sendEmailNotificationMember(content, subject);
                        }
                    }
                }
            }
        }
    }




    // method to get book price by ID
    private double getBookPriceById(String bookId) {
        // For demonstration purposes, returning a fixed price.
        return 10.0;
    }
}

