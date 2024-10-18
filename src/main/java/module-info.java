module com.example.librarymanagement2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;

    opens com.example.librarymanagement2 to javafx.fxml;
    exports com.example.librarymanagement2;
}