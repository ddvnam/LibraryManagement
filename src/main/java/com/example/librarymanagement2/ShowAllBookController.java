package com.example.librarymanagement2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowAllBookController implements Initializable {
    @FXML
    private TableView<BookItem> table;
    @FXML
    private TableColumn<BookItem, String> bookISBN;
    @FXML
    private TableColumn<BookItem, String> bookTitle;
    @FXML
    private TableColumn<BookItem, String> bookAuthor;
    @FXML
    private TableColumn<BookItem, String> bookPublisher;
    @FXML
    private TableColumn<BookItem, String> bookPublicationDate;
    @FXML
    private TableColumn<BookItem, Integer> bookCopies;
    @FXML
    private TableColumn<BookItem, Double> bookPrice;
    @FXML
    private TableColumn<BookItem, Void> bookActions; // For Edit/Delete buttons

    private ObservableList<BookItem> bookList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML ChoiceBox<String> searchType = new ChoiceBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchType.getItems().addAll("Title", "Author", "ISBN", "Publication Date");
        table.setEditable(true);
        loadData();
    }

    @FXML
    private void refreshTable() {
        // Clear the current list of books
        bookList.clear();

        // SQL query to join book and book_item tables
        String query = "SELECT * FROM book JOIN book_item ON book.book_id = book_item.book_id";

        // ResultSet initialization
        ResultSet resultSet = null;
        Database db = new Database();
        db.connectToDatabase();
        try {
            // Execute the query using db object and store the result
            resultSet = db.executeQuery(query);

            if (resultSet != null) {
                while (resultSet.next()) {
                    BookItem bookItem = new BookItem(
                            resultSet.getString("ISBN"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("publisher"),
                            resultSet.getString("publication_date"),
                            resultSet.getInt("no_of_copy"),
                            resultSet.getDouble("price")
                    );
                    // Add the created bookItem to the bookList
                    bookList.add(bookItem);
                }
            } else {
                System.out.println("No results returned from the query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        table.setItems(bookList);
        db.disConnectDatabase();
    }

    private void loadData() {
        bookISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        bookPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        bookPublicationDate.setCellValueFactory(new PropertyValueFactory<>("PublicationDate"));
        bookCopies.setCellValueFactory(new PropertyValueFactory<>("Copies"));
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        // Add actions column
        addActionsColumn();

        refreshTable();
    }

    private void addActionsColumn() {
        bookActions.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox hBox = new HBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    BookItem selectedBook = getTableView().getItems().get(getIndex());
                    handleEdit(selectedBook);
                });

                deleteButton.setOnAction(event -> {
                    BookItem selectedBook = getTableView().getItems().get(getIndex());
                    handleDelete(selectedBook);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                }
            }
        });
    }

    private void handleEdit(BookItem book) {
        TextField isbnField = new TextField(book.getISBN());
        TextField titleField = new TextField(book.getTitle());
        TextField authorField = new TextField(book.getAuthor());
        TextField publisherField = new TextField(book.getPublisher());
        TextField publicationDateField = new TextField(book.getPublicationDate());
        TextField copiesField = new TextField(String.valueOf(book.getCopies()));
        TextField priceField = new TextField(String.valueOf(book.getPrice()));

        GridPane grid = new GridPane();
        grid.addRow(0, new Label("ISBN:"), isbnField);
        grid.addRow(1, new Label("Title:"), titleField);
        grid.addRow(2, new Label("Author:"), authorField);
        grid.addRow(3, new Label("Publisher:"), publisherField);
        grid.addRow(4, new Label("Publication Date:"), publicationDateField);
        grid.addRow(5, new Label("Copies:"), copiesField);
        grid.addRow(6, new Label("Price:"), priceField);

        Alert editDialog = new Alert(Alert.AlertType.CONFIRMATION);
        editDialog.setTitle("Edit Book Details");
        editDialog.setHeaderText("Update Book Details");
        editDialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = editDialog.showAndWait();

        Database db = new Database();
        db.connectToDatabase();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            String query1 = "UPDATE book SET ISBN = '"
                    + isbnField.getText()
                    + "', title = '"
                    + titleField.getText()
                    + "', author = '"
                    + authorField.getText()
                    + "', publisher = '"
                    + publisherField.getText()
                    + "', publication_date = '"
                    + publicationDateField.getText() + "' WHERE ISBN = '" + book.getISBN() + "'";
            String query2 = "UPDATE book_item SET no_of_copy = "
                    + copiesField.getText()
                    + ", price = "
                    + priceField.getText()
                    + " WHERE book_id = (SELECT book_id FROM book WHERE ISBN = '" + isbnField.getText() + "')";
            try {
                db.executeQueryWithoutResult(query1);
                db.executeQueryWithoutResult(query2);
                refreshTable();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        db.disConnectDatabase();
    }

    private void handleDelete(BookItem book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Book");
        alert.setContentText("Are you sure you want to delete this book?");
        Optional<ButtonType> result = alert.showAndWait();
        Database db = new Database();
        db.connectToDatabase();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete from database
            String query = "DELETE FROM book WHERE ISBN = '" + book.getISBN() + "'";
            try {
                db.executeQueryWithoutResult(query);
                refreshTable();
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("An error occurred. Please try again later.");
                errorAlert.showAndWait();
            }
        }
        db.disConnectDatabase();
    }

    @FXML
    public void handleSearch(MouseEvent event) {
        String searchValue = searchField.getText().trim().toLowerCase();
        String searchTypeValue = searchType.getValue();

        if (searchTypeValue == null || searchValue.isEmpty()) {
            refreshTable();
            return;
        }

        searchValue = searchValue.replace("%", "\\%").replace("_", "\\_");

        String query = "SELECT * FROM book JOIN book_item ON book.book_id = book_item.book_id WHERE ";
        switch (searchTypeValue) {
            case "Title":
                query += "LOWER(title) LIKE ?";
                break;
            case "Author":
                query += "LOWER(author) LIKE ?";
                break;
            case "ISBN":
                query += "ISBN LIKE ?";
                break;
            case "Publication Date":
                query += "publication_date LIKE ?";
                break;
            default:
                query += "LOWER(title) LIKE ?";
                break;
        }
        Database db = new Database();
        db.connectToDatabase();
        try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
            statement.setString(1, "%" + searchValue + "%");
            ResultSet resultSet = statement.executeQuery();
            bookList.clear();

            while (resultSet.next()) {
                BookItem bookItem = new BookItem(
                        resultSet.getString("ISBN"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getString("publication_date"),
                        resultSet.getInt("no_of_copy"),
                        resultSet.getDouble("price")
                );
                bookList.add(bookItem);
            }

            if (bookList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No books found matching your criteria.");
                alert.show();
            }

            table.setItems(bookList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while executing the search query.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while fetching the search results. Please try again.");
            alert.show();
        }
        db.disConnectDatabase();
    }
}
