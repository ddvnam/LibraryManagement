module com.example.librarymanagement2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.librarymanagement2 to javafx.fxml;
    exports com.example.librarymanagement2;
}