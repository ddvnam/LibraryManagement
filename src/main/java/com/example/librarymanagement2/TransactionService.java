package com.example.librarymanagement2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private List<Transaction> transactionHistory;

    public TransactionService() {
        this.transactionHistory = new ArrayList<>();
    }

    // Method to add a transaction
    public void addTransaction(String memberId, String bookId, String transactionType) {
        String transactionId = generateTransactionId();
        LocalDateTime transactionDate = LocalDateTime.now(); // Current date and time
        Transaction transaction = new Transaction(transactionId, memberId, bookId, transactionType, transactionDate);
        transactionHistory.add(transaction);
        System.out.println("Transaction recorded: " + transaction);
    }

    // Method to generate unique transaction ID (simple incremental approach)
    private String generateTransactionId() {
        return "TXN" + (transactionHistory.size() + 1);
    }

    // Method to display all transactions
    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}


