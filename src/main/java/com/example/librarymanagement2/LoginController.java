package com.example.librarymanagement2;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import static com.example.librarymanagement2.LibraryApp.currentAccount;

public class LoginController {
    @FXML
    private AnchorPane layer1;
    @FXML
    private AnchorPane layer2;
    @FXML
    private Button signUpButton;
    @FXML
    private Button signInButton;

    //Layer2 elements
    @FXML
    private Label label2_1;
    @FXML
    private Label label2_2;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private Label forgetPassword;
    @FXML
    private Button login;
    @FXML
    private Button signUp;
    @FXML
    private Label t1;
    @FXML
    private Label t2;
    @FXML
    private ImageView emailLabel;

    @FXML
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private void initialize() {
        signUpButton.setVisible(true);
        signInButton.setVisible(false);
        email.setVisible(false);
        forgetPassword.setVisible(true);
        login.setVisible(true);
        signUp.setVisible(false);
        label2_2.setVisible(false);
        label2_1.setVisible(true);
        t1.setVisible(true);
        t2.setVisible(false);
        emailLabel.setVisible(false);
    }

    // Slide layer1 to layer2
    @FXML
    private void slideLayer1ToLayer2(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition(Duration.seconds(0.7), layer1);
        slide1.setToX(550);
        slide1.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.seconds(0.7), layer2);
        slide2.setToX(-350);
        slide2.play();
        signInButton.setVisible(true);
        signUpButton.setVisible(false);
        label2_1.setVisible(false);
        label2_2.setVisible(true);
        email.setVisible(true);
        signUp.setVisible(true);
        login.setVisible(false);
        t1.setVisible(false);
        t2.setVisible(true);
        emailLabel.setVisible(true);
        username.clear();
        password.clear();
        email.clear();
    }


    @FXML
    private void slideLayer2ToLayer1(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition(Duration.seconds(0.7), layer1);
        slide1.setToX(0);
        slide1.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.seconds(0.7), layer2);
        slide2.setToX(0);
        slide2.play();
        signInButton.setVisible(false);
        signUpButton.setVisible(true);
        label2_1.setVisible(true);
        label2_2.setVisible(false);
        email.setVisible(false);
        signUp.setVisible(false);
        login.setVisible(true);
        t1.setVisible(true);
        t2.setVisible(false);
        emailLabel.setVisible(false);
        username.clear();
        password.clear();
        email.clear();
    }

    @FXML
    private void Login(MouseEvent event) {
        try {
            String usernameText = username.getText().trim();
            String passwordText = password.getText().trim();

            // Input validation
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                displayErrorMessage("Please enter both username and password.");
                return;
            }

            // Attempt to log in
            Stage loginStage = LibraryApp.Login(usernameText, passwordText);
            if (loginStage != null) {
                if ( currentAccount.getRole().equals("librarian")) {
                    // Load the admin dashboard if the login attempt was successful
                    Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("AdminDashBoard.fxml")));
                    loginStage.setScene(newScene);
                    loginStage.show(); // Show the new scene
                } else {
                    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                    double screenWidth = screenBounds.getWidth();
                    double screenHeight = screenBounds.getHeight();

                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("home.fxml")), screenWidth, screenHeight);
                    scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
                    loginStage.setScene(scene);
                    loginStage.setTitle("Dashboard");
                    loginStage.setMaximized(true);
                    loginStage.show();
                }
            } else {
                // Display an error message if the login attempt failed
                displayErrorMessage("Invalid username or password.");
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Display a user-friendly error message
            displayErrorMessage("An error occurred. Please try again later.");
        }
    }

    @FXML void SignUp(MouseEvent event) {
        String usernameText = username.getText().trim();
        String passwordText = password.getText().trim();
        String emailText = email.getText().trim();
        if(usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty()) {
            displayErrorMessage("Please enter all required fields.");
            return;
        } else {
            if(checkEmailFormat(emailText)) {
                if(LibraryApp.Register(usernameText, passwordText, emailText)) {
                    displayErrorMessage("Account created successfully.");
                } else {
                    displayErrorMessage("Username already exists.");
                }
            } else {
                displayErrorMessage("Invalid email format.");
            }
        }
    }

    private boolean checkEmailFormat(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    private void displayErrorMessage(String message) {
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}