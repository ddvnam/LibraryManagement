package com.example.librarymanagement2;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;


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
    private Label errorMessage;

    @FXML
    private void initialize() {
        errorMessage.setVisible(false);
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
    }

    @FXML
    private void Login(MouseEvent event) {
        if (!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty()) {
            if(LibraryApp.Login(username.getText().trim(), password.getText().trim())) {
                // go to the home page
            } else {
                displayErrorMessage();
            }
        } else {
            System.out.println("Please enter username and password");
        }
    }

    @FXML void SignUp(MouseEvent event) {
        if(!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty() && !email.getText().trim().isEmpty()) {
            if(checkEmailFormat(email.getText().trim())) {
                if(LibraryApp.Register(username.getText().trim(), password.getText().trim(), email.getText().trim())) {
                    System.out.println("Account created successfully");
                } else {
                    System.out.println("Username already exists");
                }
            } else {
                System.out.println("Invalid email format");
            }
        } else {
            displayErrorMessage();
        }
    }


    private boolean checkEmailFormat(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
    private void displayErrorMessage() {
        errorMessage.setVisible(true);

        // Timeline to hide the error message after 1.5 seconds
        Timeline hideMessage = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            errorMessage.setVisible(false);
        }));
        hideMessage.setCycleCount(1);
        hideMessage.play();
    }
}
