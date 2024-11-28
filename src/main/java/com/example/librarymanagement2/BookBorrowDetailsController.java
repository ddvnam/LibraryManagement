package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookBorrowDetailsController {
    @FXML
    private Label author;

    @FXML
    private Label borrowdate;

    @FXML
    private Label description;

    @FXML
    private ImageView imageView;

    @FXML
    private Label isbn;

    @FXML
    private Label price;

    @FXML
    private Label returndate;

    @FXML
    private Label title;

    public void setDataToShow(BookItem bookItem) {
        Image image;
        try {
            String URL = bookItem.getISBN();
            image = ImageLoader.loadImageFromFile(URL);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            image = new Image("file:/path/to/default-image.png");
        }
        imageView.setImage(image);
        title.setText(bookItem.getTitle());
        author.setText(bookItem.getAuthor());
        isbn.setText("ISBN: " + bookItem.getISBN());
        price.setText("Price: " + bookItem.getPrice());
        borrowdate.setText("Publication Date: " + bookItem.getPublicationDate());
        description.setText("Description: " + bookItem.getDescription());
    }
}

