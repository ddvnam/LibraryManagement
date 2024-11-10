package com.example.librarymanagement2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryAppUI  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Library Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        Database database = new Database("jdbc:mysql://localhost:3306/librarymanagement", "root", "123456");
        database.connectToDatabase();
        database.loadData("D:\\Learning\\Java\\LibraryManagement\\src\\main\\java\\Database\\books.txt");
        //launch(args);
    }
}
