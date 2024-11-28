package com.example.librarymanagement2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.time.LocalDateTime;

public class ShowAllTransactionController {
    @FXML
    TableView<Transaction> table;
    @FXML
    TableColumn<Transaction, String> id; // Changed to String
    @FXML
    TableColumn<Transaction, String> book_id; // Changed to String
    @FXML
    TableColumn<Transaction, String> transaction_type;
    @FXML
    TableColumn<Transaction, LocalDateTime> transactionDate;
    @FXML
    TableColumn<Transaction, LocalDateTime> dueDate;
    @FXML
    TableColumn<Transaction, String> status;

    // ObservableList to hold transaction data
    private ObservableList<Transaction> listTransaction = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the TableView columns with the correct getter methods for properties
        id.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        book_id.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        transaction_type.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Set items of the TableView to the ObservableList
        table.setItems(listTransaction);

        // Load the data into the TableView
        loadTransaction();
    }

    // Method to load transactions into the ObservableList
    private void loadTransaction() {
        String query = "SELECT * FROM transaction;";
        Database db = new Database();
        db.connectToDatabase();
        System.out.println("Connected to database: " + db.getConnection());
        try {
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                String transactionId = String.valueOf(rs.getInt("transaction_id")); // Convert to String
                String bookId = String.valueOf(rs.getInt("book_item_id")); // Convert to String
                String transactionType = rs.getString("transaction_type");
                LocalDateTime transactionDate = rs.getTimestamp("transaction_date").toLocalDateTime();
                LocalDateTime dueDate = rs.getTimestamp("due_date").toLocalDateTime();
                String status = rs.getString("status");

                // Add the transaction to the ObservableList
                listTransaction.add(new Transaction(transactionId, bookId, transactionType, transactionDate, dueDate, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disConnectDatabase();
        }
    }
}
