package com.example.librarymanagement2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static com.example.librarymanagement2.LibraryApp.currentAccount;


public class BookRepository {
    private int account_id = currentAccount.getId();

    public List<BookItem> getBookItems() {
        List<BookItem> bookitems = new ArrayList<>();
        Database db = new Database();
        db.connectToDatabase();
        Connection connection = db.getConnection();

        if (connection != null) {
            String query = "select book.* , book_item.* \n" +
                    "from book\n" +
                    "join book_item on book.book_id = book_item.book_id\n" +
                    "order by book.book_id asc\n" +
                    "limit 30;";
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String ISBN = resultSet.getString("ISBN");
                    String title = resultSet.getString("title");
                    String publisher = resultSet.getString("publisher");
                    String author = resultSet.getString("author");
                    String publishedDate = resultSet.getString("publication_date");
                    double price = resultSet.getDouble("price");
                    int numOfCopies = resultSet.getInt("no_of_copy");
                    String description = resultSet.getString("description");

                    bookitems.add(new BookItem(ISBN, title, publisher, author, publishedDate, price, numOfCopies,description));
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookitems;
    }

    public List<String> Author_inf() {
        List<String> Author = new ArrayList<>();
        Database db = new Database();
        db.connectToDatabase();
        Connection connection = db.getConnection();
        if (connection != null) {
            String query = "SELECT\n" +
                    "        author,\n" +
                    "        COUNT(book.book_id) AS book_count\n" +
                    "    FROM book\n" +
                    "    GROUP BY author\n" +
                    "    ORDER BY book_count DESC\n" +
                    "    LIMIT 5;";
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String Author_name = resultSet.getString("author");
                    Author.add(Author_name);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Author;
    }

    public List<BookItem> getBooks_byAuthor(String Author) {
        List<BookItem> bookItems = new ArrayList<>();
        Database db = new Database();
        db.connectToDatabase();
        Connection connection = db.getConnection();

        if (connection != null) {
            String query = "WITH author_book_count AS (\n" +
                    "    SELECT\n" +
                    "        author,\n" +
                    "        COUNT(book.book_id) AS book_count\n" +
                    "    FROM book\n" +
                    "    GROUP BY author\n" +
                    "    ORDER BY book_count DESC\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    b.*,\n" +
                    "    book_item.*\n" +
                    "FROM book b\n" +
                    "JOIN author_book_count abc ON b.author = abc.author\n" +
                    "JOIN book_item ON b.book_id = book_item.book_id\n" +
                    "WHERE b.author = " + "'" + Author + "'\n" +
                    "ORDER BY b.author DESC;\n";
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String ISBN = resultSet.getString("ISBN");
                    String title = resultSet.getString("title");
                    String publisher = resultSet.getString("publisher");
                    String author = resultSet.getString("author");
                    String publishedDate = resultSet.getString("publication_date");
                    double price = resultSet.getDouble("price");
                    int numOfCopies = resultSet.getInt("no_of_copy");
                    String description = resultSet.getString("description");

                    bookItems.add(new BookItem(ISBN, title, publisher, author, publishedDate, price, numOfCopies,description));
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookItems;
    }

    public List<BookItem> getAllBookItems() {
        List<BookItem> bookitems = new ArrayList<>();
        Database db = new Database();
        db.connectToDatabase();
        Connection connection = db.getConnection();

        if (connection != null) {
            String query = "select book.* , book_item.* \n" +
                    "from book\n" +
                    "join book_item on book.book_id = book_item.book_id\n" +
                    "order by book.book_id asc;";
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String ISBN = resultSet.getString("ISBN");
                    String title = resultSet.getString("title");
                    String publisher = resultSet.getString("publisher");
                    String author = resultSet.getString("author");
                    String publishedDate = resultSet.getString("publication_date");
                    double price = resultSet.getDouble("price");
                    int numOfCopies = resultSet.getInt("no_of_copy");
                    String description = resultSet.getString("description");

                    bookitems.add(new BookItem(ISBN, title, publisher, author, publishedDate, price, numOfCopies, description));
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookitems;
    }

    public List<BookItem> bookBorrowed() {
        Member member = (Member) currentAccount;
        return member.getBorrowedBooks();
    }

    public List<BookItem> searchBooks(String query) {
        List<BookItem> allBooks = getAllBookItems();

        return allBooks.stream()
                .filter(book -> containsIgnoreCase(book.getTitle(), query))
                .toList();
    }

    public List<BookItem> searchAuthors(String query) {
        List<BookItem> allBooks = getAllBookItems();

        return allBooks.stream()
                .filter(book -> containsIgnoreCase(book.getAuthor(), query))
                .toList();
    }

    public List<BookItem> searchBorrowedBooks(String query) {
        List<BookItem> allBooks = bookBorrowed();

        return allBooks.stream()
                .filter(book -> containsIgnoreCase(book.getTitle(), query) || containsIgnoreCase(book.getAuthor(), query))
                .toList();
    }

    private boolean containsIgnoreCase(String source, String target) {
        if (source == null || target == null) {
            return false;
        }
        return source.toLowerCase().contains(target.toLowerCase());
    }

    public List<String> GetAuthorNameByString(String string) {
        List<String> Author = new ArrayList<>();
        Database db = new Database();
        db.connectToDatabase();
        Connection connection = db.getConnection();
        if (connection != null) {
            String query = "SELECT author, COUNT(book.book_id) AS book_count\n" +
                    "FROM book\n" +
                    "WHERE author LIKE '%" +string+"%'\n" +
                    "GROUP BY author\n" +
                    "ORDER BY book_count DESC;\n";
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String Author_name = resultSet.getString("author");
                    Author.add(Author_name);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Author;
    }
}