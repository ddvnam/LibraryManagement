package com.example.librarymanagement2;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Member extends Account{
    private int totalBooksCheckedout;
    private List<BookItem> borrowedBooks;
    private List<PortalNotification> portalNotifications;
    private double balance;
    private static final ExecutorService emailExecutor = Executors.newFixedThreadPool(10);

    public Member(String username, String password, String email) {
        super(username, password, email ,AccountStatus.ACTIVE);
        this.setRole("member");
        portalNotifications = new ArrayList<>();


        //load borrowed books
        Database db = new Database();
        db.connectToDatabase();
        try {
            String query = "SELECT * FROM book_loan " +
                    "JOIN book_item ON book_loan.book_id = book_item.book_id " +
                    "JOIN book ON book_item.book_id = book.book_id " +
                    "JOIN account ON book_loan.account_id = account.account_id " +
                    "WHERE account.username = '" + username + "';";

            ResultSet rs = db.executeQuery(query);
            borrowedBooks = new ArrayList<>();
            while (rs.next()) {
                String ISBN = rs.getString("isbn");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                String author = rs.getString("author");
                String publicationDate = rs.getString("publication_date");
                int Copies = rs.getInt("no_of_copy");
                double price = rs.getDouble("price");
                Date issueDate = rs.getDate("issue_date");
                Date dueDate = rs.getDate("due_date");
                String desciption = rs.getString("description");

                BookItem bookItem = new BookItem(ISBN, title, publisher, author, publicationDate, Copies, price, issueDate, dueDate, desciption);
                borrowedBooks.add(bookItem);
            }

            for (BookItem bookItem : borrowedBooks) {
                System.out.println(bookItem.getTitle());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "SELECT ai.balance \n" +
                "FROM account_info ai \n" +
                "JOIN account a ON ai.account_id = a.account_id \n" +
                "WHERE a.username = '" + this.getUsername() + "';";

        ResultSet rs1 = db.executeQuery(query);
        try {
            if (rs1.next()) {
                this.balance = rs1.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.disConnectDatabase();
    }

    public Member(String username) {
        this.setUsername(username);
        this.setRole("member");
    }

    public List<PortalNotification> getPortalNotifications() {
        return portalNotifications;
    }

    public void setPortalNotifications(List<PortalNotification> portalNotifications) {
        this.portalNotifications = portalNotifications;
    }

    public int getTotalBooksCheckedout() {
        return totalBooksCheckedout;
    }

    public void setBorrowedBooks(List<BookItem> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<BookItem> getBorrowedBooks() {
        return borrowedBooks;
    }


    public void searchBooks() {

    }

    /**
     * Hàm này sẽ cho phép người dùng mượn sách va tra sach.
     */
    public void borrow(String isbn) {
        boolean isBorrowed = false;
        for (BookItem bookItem : borrowedBooks) {
            if (bookItem.getISBN().equals(isbn)) {
                isBorrowed = true;
                break;
            }
        }

        if(isBorrowed) {
            String message = "Bạn đã mượn sách này rồi";
            this.showDialog(message);
            return;
        }
        Database db = new Database();
        db.connectToDatabase();
        try {
            String query = "SELECT * FROM book JOIN book_item ON book.book_id = book_item.book_id " +
                    "WHERE book.isbn = '" + isbn + "' AND book_item.status = 'available';";
            ResultSet rs = db.executeQuery(query);
            if(!rs.next()) {
                String message = "Không tìm thấy sách có mã ISBN: " + isbn;
                this.showDialog(message);
                return;
            }

            String ISBN = rs.getString("isbn");
            String title = rs.getString("title");
            String publisher = rs.getString("publisher");
            String author = rs.getString("author");
            String publicationDate = rs.getString("publication_date");
            int Copies = rs.getInt("no_of_copy");
            double price = rs.getDouble("price");


            BookItem bookItem = new BookItem(ISBN, title, publisher, author, publicationDate, Copies, price);

            borrowedBooks.add(bookItem);
            totalBooksCheckedout++;

            int book_id = rs.getInt("book_id");
            int noOfCopy = rs.getInt("no_of_copy") - 1;
            String updateQuery = "UPDATE book_item SET no_of_copy = " + noOfCopy + " WHERE book_id = '" + book_id + "'";
            db.executeQueryWithoutResult(updateQuery);

            if (noOfCopy == 0) {
                String updateStatusQuery = "UPDATE book_item SET status = 'UNAVAILABLE' WHERE book_id = " + rs.getInt("book_id");
                db.executeQueryWithoutResult(updateStatusQuery);
            }

            // Gửi thông báo
            String content = "Bạn đã mượn sách: " + bookItem.getTitle();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
            portalNotifications.add(new PortalNotification(1, new Date(), content));
        } catch (SQLException e) {
            e.printStackTrace();
            String message = "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage();
            this.showDialog(message);
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Đã xảy ra lỗi: " + e.getMessage();
            this.showDialog(message);
        } finally {
            // Đóng kết nối cơ sở dữ liệu
            db.disConnectDatabase();
        }
    }


    /**
     * Hàm này sẽ cho phép người dùng trả sách vào Catalog.
     * @param catalog Catalog chứa thông tin sách
     * @return kết quả trả sách
     */
    public void returnBook(Catalog catalog) {

    }

    public void buy(String isbn) {
        // Mua sách
        String message = "Bạn đã mua sách có mã ISBN: " + isbn;
        //tim sach can mua
        Database db = new Database();
        db.connectToDatabase();
        try {
            String query = "SELECT * FROM book JOIN book_item ON book.book_id = book_item.book_id " +
                    "WHERE book.isbn = '" + isbn + "' AND book_item.status = 'available';";
            ResultSet rs = db.executeQuery(query);
            if(!rs.next()) {
                message = "Sách đã hết hoặc không tồn tại";
                this.showDialog(message);
                return;
            }



            String ISBN = rs.getString("isbn");
            String title = rs.getString("title");
            String publisher = rs.getString("publisher");
            String author = rs.getString("author");
            String publicationDate = rs.getString("publication_date");
            int Copies = rs.getInt("no_of_copy");
            double price = rs.getDouble("price");

            BookItem bookItem = new BookItem(ISBN, title, publisher, author, publicationDate, Copies, price);
            //Kiem tra so tien
            if (this.balance < bookItem.getPrice()) {
                message = "Số dư không đủ để mua sách";
                this.showDialog(message);
                return;
            }

            //kiem tra so luong sach
            if (Copies == 0) {
                message = "Sách đã hết hoặc không tồn tại";
                this.showDialog(message);
                return;
            }
            //Cap nhat so du
            this.balance -= bookItem.getPrice();
            String updateBalanceQuery = "UPDATE account_info SET balance = " + this.balance + " WHERE account_id = " +
                    "(SELECT account_id FROM account WHERE username = '" + this.getUsername() + "')";
            db.executeQueryWithoutResult(updateBalanceQuery);
            // Gửi thông báo
            String content = "THÔNG BÁO MUA SÁCH";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
            portalNotifications.add(new PortalNotification(1, new Date(), content));
            sendEmailNotificationMember(content, bookItem, new Date());

            //giam so luong sach
            int book_id = rs.getInt("book_id");
            int noOfCopy = rs.getInt("no_of_copy") - 1;
            String updateQuery = "UPDATE book_item SET no_of_copy = " + noOfCopy + " WHERE book_id = '" + book_id + "'";
            db.executeQueryWithoutResult(updateQuery);


            //tao transaction
            int account_id = 0;
            String query1 = "SELECT account_id FROM account WHERE username = '" + this.getUsername() + "'";
            ResultSet rs1 = db.executeQuery(query1);
            if (rs1.next()) {
                account_id = rs1.getInt("account_id");
            }
            LocalDate issueDate = LocalDate.now();
            Transaction transaction = new Transaction(account_id, book_id, issueDate, price, balance - price);
            transaction.createTransaction();
            db.disConnectDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage();
            this.showDialog(message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Đã xảy ra lỗi: " + e.getMessage();
            this.showDialog(message);
        } finally {
            // Đóng kết nối cơ sở dữ liệu
            db.disConnectDatabase();
        }
    }


    /**
     *hàm sẽ return book;
     */
    public void returnBook(String isbn) {
        Database db = new Database();
        db.connectToDatabase();
        try {
            String query = "SELECT * " +
                    "FROM book_loan " +
                    "JOIN account ON book_loan.account_id = account.account_id " +
                    "JOIN book ON book_loan.book_id = book.book_id " +
                    "WHERE account.username = '" + this.getUsername() + "' AND book.isbn = '" + isbn + "';";
            ResultSet rs = db.executeQuery(query);

            if(!rs.next()) {
                String message = "Bạn chưa mượn sách này";
                this.showDialog(message);
                return;
            }

            //xoa khoi book_loan
            int book_id = rs.getInt("book_id");
            int account_id = rs.getInt("account_id");
            String deleteQuery = "DELETE FROM book_loan WHERE account_id = " + account_id + " AND book_id = " + book_id;
            db.executeQueryWithoutResult(deleteQuery);

            showDialog("Trả sách thành công");

            //Xoa khoi borrowedBooks
            borrowedBooks.removeIf(bookItem -> bookItem.getISBN().equals(isbn));

            //Cap nhat so luong sach
            String updateQuery = "UPDATE book_item SET no_of_copy = no_of_copy + 1 WHERE book_id = " + book_id;
            db.executeQueryWithoutResult(updateQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void sendEmailNotificationMember(String content, BookItem bookItem, Date date) {
        //Gửi email về portal
        String body = String.format(
                "Kính gửi Quý độc giả,\n\n"
                        + "Chúng tôi xin xác nhận rằng bạn đã mua thành công cuốn sách:\n"
                        + "Tên sách: %s\n"
                        + "Ngày mua: %s\n"
                        + "Số dư còn lại: %.2f\n"
                        + "Cảm ơn bạn đã ủng hộ thư viện của chúng tôi. Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ qua email này.\n\n"
                        + "Trân trọng,\n"
                        + "Đội ngũ quản lý thư viện",
                bookItem.getTitle(), date.toString(), this.balance
        );

        EmailNotification emailNotification = new EmailNotification(new Date(), body, content);
        EmailNotificationTask emailNotificationTask = new EmailNotificationTask(emailNotification, this);
        emailExecutor.submit(emailNotificationTask);
    }

    public void addToBookLoan(int account_id, int book_id, LocalDate issueDate) {
        Database db = new Database();
        db.connectToDatabase();

        LocalDate dueDate = issueDate.plusWeeks(2);

        String query = "INSERT INTO book_loan(account_id, book_id, issue_date, due_date) " +
                "VALUES(" + account_id + ", " + book_id + ", '" + issueDate + "', '" + dueDate + "')";

        db.executeQueryWithoutResult(query);
        db.disConnectDatabase();
    }
}
