package com.example.librarymanagement2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailNotification extends Notification {
    private static final String fromEmail = "trungdangpham03102005@gmail.com";
    private static final String password = "d a e c v m g b p i m d h d j i";
    private String subject;

    /**
     * Khoi tao mot email thong bao
     * @param notificationId: id thong bao
     * @param createdDate: ngay tao thong bao
     * @param content: noi dung thong bao
     * @param subject: chu de cua thong bao
     */
    // Constructor
    public EmailNotification(int notificationId, Date createdDate, String content, String subject) {
        super(notificationId, createdDate, content);
        this.subject = subject;
    }

    public EmailNotification(Date createdDate, String content, String subject) {
        super(createdDate, content);
        this.subject = subject;
    }


    /**
     * Gui email cho tai khoan nguoi dung
     * @param userAccount: tai khoan duoc gui thong bao toi email
     */
    @Override
    public void sendNotification(Account userAccount) {
        String toEmail = userAccount.getEmail();

        if (toEmail == null || toEmail.isEmpty()) {
            System.err.println("Error: User email is not set.");
            return;
        }

        // Vô hiệu hóa cảnh báo JavaMail
        Logger mailLogger = Logger.getLogger("javax.mail");
        mailLogger.setLevel(Level.SEVERE); // Chỉ log lỗi nghiêm trọng
        Logger.getLogger("javax.activation").setLevel(Level.SEVERE);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);

            // Đặt địa chỉ người gửi với tên hiển thị là "Library Notification System"
            msg.setFrom(new InternetAddress(fromEmail, "TRUNG TÂM THƯ VIỆN VÀ TRI THỨC SỐ"));

            // Đặt địa chỉ người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Đặt chủ đề (subject) của email
            msg.setSubject(subject, "UTF-8");

            // Đặt nội dung email
            msg.setText(content, "UTF-8");

            // Đặt ngày gửi email
            msg.setSentDate(createdDate);

            Transport.send(msg);
            System.out.println("Email sent successfully to: " + toEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public void sendEmail(String toEmail) {
        // Check if the email is null or empty
        if (toEmail == null || toEmail.isEmpty()) {
            System.err.println("Error: Email is not provided.");
            return;
        }

        // Vô hiệu hóa cảnh báo JavaMail
        Logger mailLogger = Logger.getLogger("javax.mail");
        mailLogger.setLevel(Level.SEVERE); // Chỉ log lỗi nghiêm trọng
        Logger.getLogger("javax.activation").setLevel(Level.SEVERE);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);

            // Đặt địa chỉ người gửi với tên hiển thị là "Library Notification System"
            msg.setFrom(new InternetAddress(fromEmail, "TRUNG TÂM THƯ VIỆN VÀ TRI THỨC SỐ"));

            // Đặt địa chỉ người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Đặt chủ đề (subject) của email
            msg.setSubject(subject, "UTF-8");

            // Đặt nội dung email
            msg.setText(content, "UTF-8");

            // Đặt ngày gửi email
            msg.setSentDate(createdDate);

            // Gửi email
            Transport.send(msg);
            System.out.println("Email sent successfully to: " + toEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
