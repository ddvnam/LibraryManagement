package com.example.librarymanagement2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    //Constructor

    public Database() {
    }

    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    //Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    //Methods
    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disConnectDatabase() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean executeUpdate(String query) {
        try {
            connection.createStatement().executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void loadData(List<Book> bookList, String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = 0;

            // Read each line in the file
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                // Ensure that the array has the correct length
                if (data.length < 5) {
                    System.err.println("Invalid line format: " + line);
                    continue; // Skip this line if it doesn't have enough data
                }

                // Create a new Author for each book
                Author author = new Author();
                author.setName(data[3]);

                // Create a new Book object
                Book book = new Book(data[0], data[1], data[2], author, data[4]);
                bookList.add(book);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }
    }
}