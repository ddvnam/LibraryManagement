package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CardBorrowBookController {

    @FXML
    private HBox bookBorrow_HBox;

    @FXML
    private Label bookBorrow_author;

    @FXML
    private ImageView bookBorrow_image;

    @FXML
    private Label bookBorrow_title;

    @FXML
    private Label borrowDate;

    @FXML
    private Label returnDate;

    public void bookBorrowed_setData(BookItem bookItem) {
        Image image;
        try {
            String URL = bookItem.getISBN();
            image = ImageLoader.loadImageFromFile(URL);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            image = new Image("file:/path/to/default-image.png");
        }
        bookBorrow_image.setImage(image);
        bookBorrow_title.setText(bookItem.getTitle());
        bookBorrow_author.setText(bookItem.getAuthor());
        HBox.setHgrow(bookBorrow_HBox, Priority.ALWAYS);
        bookBorrow_HBox.setMaxWidth(Double.MAX_VALUE);
        bookBorrow_HBox.setMaxHeight(Double.MAX_VALUE);
        bookBorrow_HBox.getStyleClass().add("background_and_hover");
    }

}
