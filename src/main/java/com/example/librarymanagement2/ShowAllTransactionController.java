package com.example.librarymanagement2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ShowAllTransactionController {
    @FXML
    private TableView<Transaction> table;
    @FXML
    private TableColumn<Transaction, Integer> account_id;
    @FXML
    private TableColumn<Transaction, Integer> book_id;
    @FXML
    private TableColumn<Transaction, LocalDate> transactionDate;
    @FXML
    private TableColumn<Transaction, Double> total;
    @FXML
    private TableColumn<Transaction, Double> remain;

    // ObservableList to hold transaction data
    private ObservableList<Transaction> listTransaction = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the TableView columns with the correct getter methods for properties
        account_id.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        book_id.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        remain.setCellValueFactory(new PropertyValueFactory<>("remain"));

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
                int memberId = rs.getInt("account_id");
                int bookId = rs.getInt("book_id");
                LocalDate transactionDate = rs.getDate("transaction_date").toLocalDate();
                double total = rs.getDouble("total");
                double remain = rs.getDouble("remain");

                // Add new Transaction to the ObservableList
                listTransaction.add(new Transaction(memberId, bookId, transactionDate, total, remain));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing query: " + e.getMessage());
        } finally {
            db.disConnectDatabase();
        }
    }
}
