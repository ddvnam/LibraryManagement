package com.example.librarymanagement2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

public class BookDetailsController {

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookPrice;

    @FXML
    private Label bookISBN;

    @FXML
    private Label bookPublisher;

    @FXML
    private Label bookYear;

    @FXML
    private Label bookNumBerOfCopies;

    @FXML
    private Label bookDescription;

    @FXML
    private Button buttonBack;

    public void setBookDetails(BookItem bookItem, EventHandler<ActionEvent> backActionHandler) {
        Image image;
        try {
            String URL = bookItem.getISBN();
            image = ImageLoader.loadImageFromFile(URL);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            image = new Image("file:/path/to/default-image.png");
        }
        bookImage.setImage(image);
        bookTitle.setText(bookItem.getTitle());
        bookAuthor.setText("Author: " + bookItem.getAuthor());
        bookPrice.setText("Price: $" + bookItem.getPrice());
        bookISBN.setText("ISBN: " + bookItem.getISBN());
        bookPublisher.setText("Publisher: " + bookItem.getPublisher());
        bookYear.setText("Publication Date: " + bookItem.getPublicationDate());
        bookNumBerOfCopies.setText("Available: " + bookItem.getCopies());
        bookDescription.setText("Description: "+ bookItem.getDescription());
        buttonBack.setOnAction(backActionHandler);
    }
}
