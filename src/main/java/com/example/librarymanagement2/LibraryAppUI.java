package com.example.librarymanagement2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.librarymanagement2.LibraryApp.*;

public class LibraryAppUI  extends Application {
    @Override
    public void start(Stage Primarystage) throws IOException {
        try {
            //fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            fxmlLoader = new FXMLLoader(getClass().getResource("AdminDashBoard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = Primarystage;
            stage.setTitle("Library Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        db.connectToDatabase();
        launch(args);
    }
}
