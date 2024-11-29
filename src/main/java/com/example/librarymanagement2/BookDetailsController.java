package com.example.librarymanagement2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import static com.example.librarymanagement2.LibraryApp.*;
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

    @FXML
    private Button borrowButton;

    @FXML
    private Button buyButton;

    @FXML
    private Button Borrowed;
    @FXML
    public void initialize() {
        String isbnText = bookISBN.getText();
        if (isbnText != null && isbnText.length() > 6) {
            String isbn = isbnText.substring(6);
            Member member = (Member) currentAccount;
            // Check in the borrowed book list
            List<BookItem> borrowedBooks = member.getBorrowedBooks();


            for (BookItem bookItem : borrowedBooks) {
                if (bookItem.getISBN().equals(isbn)) {
                    borrowButton.setDisable(true);
//                    Borrowed.setDisable(false);
//                    borrowButton.setVisible(false);
//                    Borrowed.setVisible(true);
                    break;
                }
            }
//            Borrowed.setVisible(false);
            borrowButton.setVisible(true);
        } else {
            System.out.println("Invalid ISBN text: " + isbnText);
        }
    }


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
        bookDescription.setText("Description:"+bookItem.getDescription());
        buttonBack.setOnAction(backActionHandler);
    }

    @FXML
    public void handleBorrow(MouseEvent event) {
        String isbn = bookISBN.getText().substring(6);
        Member member = (Member) currentAccount;
        member.borrow(isbn);

        String query = "SELECT book_id FROM book WHERE isbn = '" + isbn + "';";
        String query1 = "SELECT account_id FROM account WHERE username = '" + currentAccount.getUsername() + "';";
        Database db = new Database();
        db.connectToDatabase();
        ResultSet rs = db.executeQuery(query);
        ResultSet rs1 = db.executeQuery(query1);
        int book_id = 0;
        int account_id = 0;
        try {
            if (rs.next()) {
                book_id = rs.getInt("book_id");
            }
            if (rs1.next()) {
                account_id = rs1.getInt("account_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocalDate issue_date = LocalDate.now();
        member.addToBookLoan(account_id, book_id, issue_date);
        initialize();
    }

    @FXML
    public void handleBuy(MouseEvent event) {
        String isbn = bookISBN.getText().substring(6);
        Member member = (Member) currentAccount;
        member.buy(isbn);
    }
}
