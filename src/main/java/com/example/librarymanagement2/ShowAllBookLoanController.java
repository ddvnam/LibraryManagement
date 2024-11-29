package com.example.librarymanagement2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.time.LocalDate;

public class ShowAllBookLoanController {
    @FXML
    TableView<BookLoan> table;
    @FXML
    TableColumn<BookLoan, Integer> account_id;
    @FXML
    TableColumn<BookLoan, Integer> book_id;
    @FXML
    TableColumn<BookLoan, LocalDate> issueDate;
    @FXML
    TableColumn<BookLoan, LocalDate> dueDate;

    // ObservableList to hold book loan data
    private ObservableList<BookLoan> listBookLoan = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the TableView columns with the correct getter methods for properties
        account_id.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        book_id.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // Set items of the TableView to the ObservableList
        table.setItems(listBookLoan);

        // Load the data into the TableView
        loadBookLoans();
    }

    // Method to load book loans into the ObservableList
    private void loadBookLoans() {
        String query = "SELECT * FROM book_loan;";
        Database db = new Database();
        db.connectToDatabase();
        System.out.println("Connected to database: " + db.getConnection());
        try {
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                int accountId = rs.getInt("account_id");
                int bookId = rs.getInt("book_id");
                LocalDate issueDate = rs.getDate("issue_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();

                // Add the book loan record to the list
                listBookLoan.add(new BookLoan(accountId, bookId, issueDate, dueDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disConnectDatabase();
        }
    }
}
