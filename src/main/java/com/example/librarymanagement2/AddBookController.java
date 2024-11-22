package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import static com.example.librarymanagement2.LibraryApp.*;
public class AddBookController {
    @FXML
    private TextField ISBN;
    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextField publishcationDate;
    @FXML
    private TextField publisher;
    @FXML
    private TextField image_url;
    @FXML
    private TextField noOfCopies;
    @FXML
    private TextField price;
    @FXML
    private Button addBookButton;

    public void addBook(MouseEvent event) {
        // Trim and sanitize input
        String isbn = ISBN.getText().trim().replace("'", "''");
        String title = this.title.getText().trim().replace("'", "''");
        String author = this.author.getText().trim().replace("'", "''");
        String publicationDate = publishcationDate.getText().trim();
        String publisher = this.publisher.getText().trim().replace("'", "''");
        String imageUrl = image_url.getText().trim().replace("'", "''");
        String noOfCopiesText = this.noOfCopies.getText().trim();
        String priceText = this.price.getText().trim();

        // Check for empty fields
        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publicationDate.isEmpty() ||
                publisher.isEmpty() || imageUrl.isEmpty() || noOfCopiesText.isEmpty() || priceText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Parse numbers
        try {
            int noOfCopies = Integer.parseInt(noOfCopiesText);
            double price = Double.parseDouble(priceText);

            // Perform the addition
            Librarian librarian = (Librarian) currentAccount;
            BookItem bookItem = new BookItem(isbn, title, publisher, author, publicationDate, price, noOfCopies, imageUrl);
            librarian.addBookItem(bookItem);

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Success");
            alert.setHeaderText("Book added successfully.");
            alert.showAndWait();

            // Clear all fields
            ISBN.clear();
            this.title.clear();
            this.author.clear();
            publishcationDate.clear();
            this.publisher.clear();
            image_url.clear();
            this.noOfCopies.clear();
            this.price.clear();

        } catch (NumberFormatException e) {
            // Handle invalid number formats
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid number format for 'No of Copies' or 'Price'.");
            alert.showAndWait();
        }
    }
}
