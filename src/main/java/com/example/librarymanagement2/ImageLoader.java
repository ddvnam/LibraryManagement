package com.example.librarymanagement2;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ImageLoader {
    // Method to load an image from a local file using the ISBN as the filename
    public static Image loadImageFromFile(String isbn) throws IOException {
        // Define the directory where images are stored
        String downloadDir = "D:\\Learning\\Java\\OOP\\LibraryManagement\\src\\main\\resources\\book_images";

        // Create the file path by combining the directory and the ISBN
        File imageFile = new File(downloadDir, isbn + ".jpg");

        // Check if the file exists
        if (!imageFile.exists()) {
            throw new IOException("Image file not found for ISBN: " + isbn);
        }

        // Load and return the image
        return new Image(new FileInputStream(imageFile));
    }
}
