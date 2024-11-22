package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminDashBoardController {

    @FXML
    private AnchorPane contentPane; // This is the dynamic area where FXMLs will be loaded
    @FXML
    private Button searchButton;
    @FXML
    private Button addBookButton;
    @FXML
    private Button removeBookButton;
    @FXML
    private Button updateBookButton;
    @FXML
    private Button showallAccountButton;
    @FXML
    private Button viewTransactionButton;
    @FXML
    private Button showAllBookButton;
    @FXML
    private ChoiceBox<String> searchType = new ChoiceBox<>();
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> pubDateColumn;


    @FXML
    private void initialize() {
        searchType.getItems().addAll("Title", "Author", "ISBN", "Publication Date");
        searchType.setValue("Title");

        // Set up the columns in the TableView
        table = new TableView<>();
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, String> pubDateColumn = new TableColumn<>("Publication Date");
        pubDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
    }

    public void changeScene(MouseEvent event) {
        try {
            if (event.getSource() == searchButton) {
                loadScene("SearchResult.fxml");
            } else if (event.getSource() == addBookButton) {
                loadScene("AddBook.fxml");
            } else if (event.getSource() == removeBookButton) {
                loadScene("RemoveBook.fxml");
            } else if (event.getSource() == updateBookButton) {
                loadScene("UpdateBook.fxml");
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

        // Ensure the loaded FXML fills the contentPane
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
    }

    private void handleSearchButton() {
        try {
            String searchType = this.searchType.getValue();
            if(searchType == null) {
                System.err.println("Search type is not selected");
                return;
            }

            loadScene("SearchResult.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + e.getMessage());
        }
    }
}
