package com.example.librarymanagement2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    public static Database database = new Database();
    public static Catalog catalog = new Catalog();
    public static Account currentAccount;
    //data
    private static String URL = "jdbc:mysql://localhost:3306/librarymanagement";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    public static final Database db = new Database(URL, USERNAME, PASSWORD);

    //UI
    public static FXMLLoader fxmlLoader;
    public static Parent root;
    public static Scene scene;
    public static Stage stage;

    public static Stage Login(String username, String password) {
        String query = String.format(
                "SELECT account.username, account.password, account_info.email, account.role " +
                        "FROM account JOIN account_info ON account.account_id = account_info.account_id " +
                        "WHERE account.username = '%s' AND account.password = '%s'",
                username, password
        );
        try {
            ResultSet account = db.executeQuery(query);
            if (account == null) {
                return null;
            } else {
                if(!account.next()) {
                    return null;
                }
                currentAccount = new Account(account.getString("username"), account.getString("password"), account.getString("email"), account.getString("role"));
                System.out.println("Login successful");
                return stage;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Da thay doi */
    public static boolean Register(String username, String password, String email) {
        return true;
    }

}
