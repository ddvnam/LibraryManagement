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
    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
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

    // Method to search transactions by book ID
    public List<Transaction> searchTransactionsByBook(String bookId) {
        return transactionHistory.stream()
                .filter(transaction -> transaction.getBookId().equals(bookId))
                .collect(Collectors.toList());
    }

    // Method to generate and send monthly statements to members
    public void generateMonthlyStatements() {
        Map<String, List<Transaction>> memberTransactionsMap = new HashMap<>();

        // Group transactions by member ID
        for (Transaction transaction : transactionHistory) {
            if (!memberTransactionsMap.containsKey(transaction.getMemberId())) {
                memberTransactionsMap.put(transaction.getMemberId(), new ArrayList<>());
            }
            memberTransactionsMap.get(transaction.getMemberId()).add(transaction);
        }

        // Generate statements for each member
        for (Map.Entry<String, List<Transaction>> entry : memberTransactionsMap.entrySet()) {
            String memberId = entry.getKey();
            List<Transaction> transactions = entry.getValue();

            System.out.println("\nMonthly Statement for Member ID: " + memberId);
            System.out.println("------------------------------------------------");
            double totalBorrowedCost = 0;
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
                if (transaction.getTransactionType().equals("BORROW")) {
                    // Assuming we have a way to get the cost of the book from the book ID (pseudo-code)
                    double bookCost = getBookPriceById(transaction.getBookId());
                    totalBorrowedCost += bookCost;
                }
            }
            System.out.println("Total Borrowed Cost: " + totalBorrowedCost);
            System.out.println("------------------------------------------------");
        }
    }

    // Pseudo-method to get book price by ID (this should be linked to the actual catalog)
    private double getBookPriceById(String bookId) {
        // For demonstration purposes, returning a fixed price.
        return 10.0;
    }
}


