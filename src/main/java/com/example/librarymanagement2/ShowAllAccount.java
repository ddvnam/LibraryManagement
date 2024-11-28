package com.example.librarymanagement2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

public class ShowAllAccount {
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account, String> usernameColumn;
    @FXML
    private TableColumn<Account, String> passwordColumn;
    @FXML
    private TableColumn<Account, String> emailColumn;
    @FXML
    private TableColumn<Account, String> roleColumn;
    @FXML
    private TableColumn<Account, String> statusColumn;
    @FXML
    private TableColumn<Account, String> idColumn;

    // ObservableList to hold account data
    private ObservableList<Account> listAccount = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the TableView columns with the correct getter methods for properties
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Set items of the TableView to the ObservableList
        accountTable.setItems(listAccount);

        // Load the data into the TableView
        loadAccounts();
    }


    // Method to load accounts into the ObservableList
    private void loadAccounts() {
        String query = "select account.*,  account_info.email from account join account_info on account.account_id = account_info.account_id;";
        Database db = new Database();
        db.connectToDatabase();
        System.out.println("Connected to database" + db.getConnection());
        try {
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String status = rs.getString("status");
                int id = rs.getInt("account_id");

                // Add the account to the ObservableList
                listAccount.add(new Account(id, username, password, email, role, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.disConnectDatabase();
    }


}
