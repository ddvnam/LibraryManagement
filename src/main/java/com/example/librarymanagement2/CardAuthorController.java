package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CardAuthorController {
    @FXML
    private HBox bookAuthor_HBox;

    @FXML
    private Label bookAuthor_author;

    @FXML
    private ImageView bookAuthor_image;

    @FXML
    private Label bookAuthor_title;

    @FXML
    private Label bookAuthor_year;

    private String [] colors = { "#F5827D", "#FC909A", "#F8A7A6", "#F9CDAD", "#C6C6AA", "#83AF9B", "#F9B294", "#FDCEAA", "#557C81",
                                 "#ADDCC8", "#9AB999", "#3AAFA9", "#DCEBC1", "#A8A8A8", "#CD537C", "#F88F3C", "#F6DB68", "#F26A44",
                                 "#EBE774", "#E1EEC3", "#F1737F", "#F6D520", "#E6EFC2", "#A2D5AC", "#C06C86", "#FE4365", "#2E9699"};

    public void bookAuthor_setData(BookItem bookItem) {
        Image image;
        try {
            String URL = bookItem.getISBN();
            image = ImageLoader.loadImageFromFile(URL);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            image = new Image("file:/path/to/default-image.png");
        }
        bookAuthor_image.setImage(image);
        bookAuthor_title.setText(bookItem.getTitle());
        bookAuthor_year.setText(bookItem.getPublicationDate());
        bookAuthor_author.setText(bookItem.getAuthor());
        bookAuthor_HBox.setStyle("-fx-background-color: " + colors[(int)(Math.random() * colors.length)]);
    }
}
