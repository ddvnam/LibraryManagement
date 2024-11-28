package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashBoardController {
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button searchButton;
    @FXML
    private Button addBookButton;
    @FXML
    private Button showallAccountButton;
    @FXML
    private Button viewTransactionButton;
    @FXML
    private Button showAllBookButton;
    @FXML
    private Button logoutButton;
    @FXML
    private void initialize() {

    }

    public void changeScene(MouseEvent event) {
        try {
            if (event.getSource() == searchButton) {
                loadScene("SearchResult.fxml");
            } else if (event.getSource() == addBookButton) {
                loadScene("AddBook.fxml");
            } else if (event.getSource() == showallAccountButton) {
                loadScene("ShowAllAccount.fxml");
            } else if (event.getSource() == viewTransactionButton) {
                loadScene("ViewTransaction.fxml");
            } else if (event.getSource() == showAllBookButton) {
                loadScene("ShowAllBook.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + e.getMessage());
        }
    }

    private void loadScene(String fxml) throws IOException {
        // Load the new FXML file into a Parent node
        Parent root = FXMLLoader.load(getClass().getResource(fxml));

        // Clear the existing content
        contentPane.getChildren().clear();

        // Add the new content
        contentPane.getChildren().add(root);
        System.out.println("Loaded " + fxml);
        // Ensure the loaded FXML fills the contentPane
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
    }

    public void handleLogout(MouseEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + e.getMessage());
        }
    }
}