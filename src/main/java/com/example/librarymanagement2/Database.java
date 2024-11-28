package com.example.librarymanagement2;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.sql.ResultSet;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    //Constructor

    public Database() {
        this.url = "jdbc:mysql://localhost:3307/librarymanagement";
        this.username = "root";
        this.password = "Quangminh1@";
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
            if (connection != null) {
                System.out.println("Connected to the database");
            }
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


    public void executeQueryWithoutResult(String query) {
        try {
            connection.createStatement().execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}