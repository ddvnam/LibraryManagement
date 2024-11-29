package com.example.librarymanagement2;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleBookApi {
    private static ArrayList<Double> randomPrice = new ArrayList<Double>();
    static {
        randomPrice.add(59.0);
        randomPrice.add(159.0);
        randomPrice.add(69.0);
        randomPrice.add(79.0);
        randomPrice.add(99.0);
    }

    public static Book getBookDetailsByISBN(String isbn) {
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;

        try {
            // Open connection to the API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.has("items")) {
                    JSONArray items = jsonResponse.getJSONArray("items");
                    JSONObject bookInfo = items.getJSONObject(0).getJSONObject("volumeInfo");

                    // Extract book details
                    String title = bookInfo.optString("title", "No title available");
                    String publisher = bookInfo.optString("publisher", "No publisher available");
                    String author = getAuthors(bookInfo);
                    String publicationDate = getPublicationYear(bookInfo);
                    String imageUrl = getImageUrl(bookInfo);
                    String description = getDescription(bookInfo);

                    // Create and return a new Book object
                    return new Book(isbn, title, publisher, author, publicationDate, imageUrl, description);
                } else {
                    System.out.println("No book found for the provided ISBN.");
                }
            } else {
                System.out.println("Error: Unable to fetch data. HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Return null if no book found or error occurs
    }

    private static String getAuthors(JSONObject bookInfo) {
        // Get the authors, if available, otherwise return "No author available"
        if (bookInfo.has("authors")) {
            JSONArray authorsArray = bookInfo.getJSONArray("authors");
            StringBuilder authors = new StringBuilder();
            for (int i = 0; i < authorsArray.length(); i++) {
                authors.append(authorsArray.getString(i));
                if (i < authorsArray.length() - 1) {
                    authors.append(", ");
                }
            }
            return authors.toString();
        }
        return "No author available";
    }

    private static String getPublicationYear(JSONObject bookInfo) {
        // Extract only the year from the publication date, if available
        String publicationDate = bookInfo.optString("publishedDate", "No publication date available");
        if (publicationDate.contains("-")) {
            return publicationDate.split("-")[0];  // Extract year
        }
        return publicationDate;  // Return full date if no "-" found
    }

    private static String getImageUrl(JSONObject bookInfo) {
        // Check if imageLinks is available and extract the image URL
        if (bookInfo.has("imageLinks")) {
            JSONObject imageLinks = bookInfo.getJSONObject("imageLinks");
            if (imageLinks.has("thumbnail")) {
                return imageLinks.getString("thumbnail");
            }
        }
        return "No image available";  // Default value if no image found
    }

    private static String getDescription(JSONObject bookInfo) {
        // Get the description, if available
        return bookInfo.optString("description", "No description available");
    }

    public static void update_db() {
        String filename = "D:\\Learning\\Python\\LibraryManagement\\src\\main\\java\\Database\\books.txt";
        ArrayList<String> isbns = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip the first line (header or irrelevant line)
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String isbn = parts[0];
                isbns.add(isbn);
                if (isbns.size() == 5000) { // Giới hạn 5000 sách
                    break;
                }
            }
            System.out.println("Total books: " + isbns.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Câu lệnh UPDATE để cập nhật mô tả sách
        String updateQuery = "UPDATE book SET description = ? WHERE isbn = ?";
        Database db = new Database();
        db.connectToDatabase();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Kiểm tra kết nối cơ sở dữ liệu
            conn = db.getConnection();
            if (conn == null) {
                System.out.println("Failed to establish a database connection!");
                return;  // Nếu không kết nối được, thoát khỏi phương thức
            }

            // Duyệt qua danh sách ISBN và cập nhật thông tin mô tả
            for (int i = 1811; i < isbns.size(); i++) {
                String isbn = isbns.get(i);
                Book book = getBookDetailsByISBN(isbn);
                if (book != null) {
                    try {
                        // Chuẩn bị câu lệnh SQL
                        ps = conn.prepareStatement(updateQuery);
                        ps.setString(1, book.getDescription());
                        ps.setString(2, isbn);

                        // Thực thi cập nhật
                        int rowsUpdated = ps.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Updated book with ISBN: " + isbn);
                        } else {
                            System.out.println("No book found for ISBN: " + isbn);
                        }
                    } catch (SQLException e) {
                        System.err.println("Error updating book with ISBN: " + isbn);
                        e.printStackTrace();
                    }

                    // Thêm delay giữa các lần gọi API để tránh bị giới hạn
                    try {
                        Thread.sleep(500); // Delay 1 giây (1000ms)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Book not found for ISBN: " + isbn);
                }
                System.out.println("Books updated: " + i);
            }
        } catch (Exception e) {
            System.err.println("Error getting database connection");
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tài nguyên sau khi sử dụng
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        update_db();
    }
}

