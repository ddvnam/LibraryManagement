package com.example.librarymanagement2;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import static com.example.librarymanagement2.LibraryApp.currentAccount;
import javafx.geometry.Insets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginController {
    @FXML
    private AnchorPane layer1;
    @FXML
    private AnchorPane layer2;
    @FXML
    private Button signUpButton;
    @FXML
    private Button signInButton;

    //Layer2 elements
    @FXML
    private Label label2_1;
    @FXML
    private Label label2_2;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private Label forgetPassword;
    @FXML
    private Button login;
    @FXML
    private Button signUp;
    @FXML
    private Label t1;
    @FXML
    private Label t2;
    @FXML
    private ImageView emailLabel;

    @FXML
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private void initialize() {
        signUpButton.setVisible(true);
        signInButton.setVisible(false);
        email.setVisible(false);
        forgetPassword.setVisible(true);
        login.setVisible(true);
        signUp.setVisible(false);
        label2_2.setVisible(false);
        label2_1.setVisible(true);
        t1.setVisible(true);
        t2.setVisible(false);
        emailLabel.setVisible(false);
    }

    // Slide layer1 to layer2
    @FXML
    private void slideLayer1ToLayer2(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition(Duration.seconds(0.7), layer1);
        slide1.setToX(550);
        slide1.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.seconds(0.7), layer2);
        slide2.setToX(-350);
        slide2.play();
        signInButton.setVisible(true);
        signUpButton.setVisible(false);
        label2_1.setVisible(false);
        label2_2.setVisible(true);
        email.setVisible(true);
        signUp.setVisible(true);
        login.setVisible(false);
        t1.setVisible(false);
        t2.setVisible(true);
        emailLabel.setVisible(true);
        username.clear();
        password.clear();
        email.clear();
    }


    @FXML
    private void slideLayer2ToLayer1(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition(Duration.seconds(0.7), layer1);
        slide1.setToX(0);
        slide1.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.seconds(0.7), layer2);
        slide2.setToX(0);
        slide2.play();
        signInButton.setVisible(false);
        signUpButton.setVisible(true);
        label2_1.setVisible(true);
        label2_2.setVisible(false);
        email.setVisible(false);
        signUp.setVisible(false);
        login.setVisible(true);
        t1.setVisible(true);
        t2.setVisible(false);
        emailLabel.setVisible(false);
        username.clear();
        password.clear();
        email.clear();
    }

    @FXML
    private void Login(MouseEvent event) {
        try {
            String usernameText = username.getText().trim();
            String passwordText = password.getText().trim();

            // Input validation
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                displayErrorMessage("Please enter both username and password.");
                return;
            }

            // Attempt to log in
            Stage loginStage = LibraryApp.Login(usernameText, passwordText);
            if (loginStage != null) {
                if ( currentAccount.getRole().equals("librarian")) {
                    // Load the admin dashboard if the login attempt was successful
                    Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("AdminDashBoard.fxml")));
                    loginStage.setScene(newScene);
                    loginStage.show(); // Show the new scene
                } else {
                    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                    double screenWidth = screenBounds.getWidth();
                    double screenHeight = screenBounds.getHeight();

                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("home.fxml")), screenWidth, screenHeight);
                    scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
                    loginStage.setScene(scene);
                    loginStage.setTitle("Dashboard");
                    loginStage.setMaximized(true);
                    loginStage.show();
                }
            } else {
                // Display an error message if the login attempt failed
                displayErrorMessage("Invalid username or password.");
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Display a user-friendly error message
            displayErrorMessage("An error occurred. Please try again later.");
        }
    }

    @FXML void SignUp(MouseEvent event) {
        String usernameText = username.getText().trim();
        String passwordText = password.getText().trim();
        String emailText = email.getText().trim();
        if(usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty()) {
            displayErrorMessage("Please enter all required fields.");
            return;
        } else {
            if(checkEmailFormat(emailText)) {
                if(LibraryApp.Register(usernameText, passwordText, emailText)) {
                    displayErrorMessage("Account created successfully.");
                } else {
                    displayErrorMessage("Username already exists.");
                }
            } else {
                displayErrorMessage("Invalid email format.");
            }
        }
    }

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    private boolean checkEmailFormat(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    private void displayErrorMessage(String message) {
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleForgetPassword(MouseEvent event) {
        // Tạo dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Forget Password");
        dialog.setHeaderText("Enter your username and email to reset your password");

        // Nút gửi
        ButtonType sendButtonType = new ButtonType("Send Email", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(sendButtonType, ButtonType.CANCEL);

        // Tạo layout cho username và email
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Xử lý khi bấm nút gửi
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == sendButtonType) {
                String username = usernameField.getText().trim();
                String email = emailField.getText().trim();
                if (isValidUsernameAndEmail(username, email)) {
                    sendEmail(email);
                    showErrorAlert("An email has been sent to your email address.");
                } else {
                    showErrorAlert("Invalid username or email!");
                }
            }
            return null;
        });

        // Hiển thị dialog
        dialog.showAndWait();
    }

    // Hàm giả lập kiểm tra username và email
    private boolean isValidUsernameAndEmail(String username, String email) {
        // Kiem tra username va email co ton tai trong database
        String query = "SELECT * FROM account " +
                "JOIN account_info ON account.account_id = account_info.account_id " +
                "WHERE account.username = ? AND account_info.email = ?;";
        Database db = new Database();
        try {
            db.connectToDatabase();
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disConnectDatabase(); // Ensure the database connection is closed
        }
        return false;
    }


    // Hàm gửi email giả lập
    private void sendEmail(String email) {
        Date date = new Date();
        String subject = "THÔNG BÁO ĐỔI MẬT KHẨU";
        System.out.println(date.toString());

        //get password from database
        String query = "SELECT * FROM account " +
                "JOIN account_info ON account.account_id = account_info.account_id " +
                "WHERE account_info.email = ?;";
        Database db = new Database();
        try {
            db.connectToDatabase();
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                String username = rs.getString("username");
                String content = String.format(
                        "Kính gửi Quý bạn đọc,\n\n" +
                                "Cảm ơn bạn đã sử dụng dịch vụ của Trung tâm Thư viện và Tri thức số.\n" +
                                "Dưới đây là thông tin tài khoản của bạn:\n\n" +
                                "- Tên tài khoản: %s\n" +
                                "- Mật khẩu: %s\n\n" +
                                "Vui lòng giữ thông tin này an toàn và không chia sẻ với bất kỳ ai.\n\n" +
                                "Nếu bạn không yêu cầu nhận thông tin tài khoản này hoặc phát hiện có hành động truy cập trái phép, hãy liên hệ ngay với chúng tôi để được hỗ trợ.\n\n" +
                                "Trân trọng,\n---\n" +
                                "Trung tâm Thư viện và Tri thức số\n" +
                                "Email: support@library.edu.vn\n" +
                                "Hotline: 1800-123-456",
                        username,
                        password
                );

                EmailNotification emailNotification = new EmailNotification(date, content, subject);
                Account userAccount = new Account(rs.getString("username"), rs.getString("password"),rs.getString("email"),rs.getString("role"));
                EmailNotificationTask emailNotificationTask = new EmailNotificationTask(emailNotification, userAccount);
                executor.submit(emailNotificationTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disConnectDatabase(); // Ensure the database connection is closed
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}