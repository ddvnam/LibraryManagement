module com.example.librarymanagement2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires org.json;
    requires java.desktop;
    requires com.google.zxing;

    opens com.example.librarymanagement2 to javafx.fxml;
    exports com.example.librarymanagement2;
}