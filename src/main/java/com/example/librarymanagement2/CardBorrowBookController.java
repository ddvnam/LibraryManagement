package com.example.librarymanagement2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.librarymanagement2.LibraryApp.currentAccount;
import com.example.librarymanagement2.HelloController;
import javafx.stage.Stage;

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
    private Label dueDate;

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
        borrowDate.setText("Borrow Date: " + bookItem.getBorrowDate());
        dueDate.setText("Due Date: " + bookItem.getDueDate());
    }

    @FXML
    public void returnBook(MouseEvent event) throws SQLException {
        // Return book
        //get isbn from title

        Database db = new Database();
        db.connectToDatabase();
        String title = bookBorrow_title.getText();
        String isbn = "";
        String query = "SELECT * FROM " + "book" + " WHERE " + "title" + " = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isbn = rs.getString("isbn");
            }
        };

        Member member = (Member) currentAccount;
        member.returnBook(isbn);
        changeScene("myBookShelf.fxml");
    }

    private void changeScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) bookBorrow_HBox.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
