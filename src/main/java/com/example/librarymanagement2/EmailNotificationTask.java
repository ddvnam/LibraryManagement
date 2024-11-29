package com.example.librarymanagement2;

public class EmailNotificationTask implements Runnable {
    private EmailNotification emailNotification;
    private Account userAccount;

    /**
     * Khởi tạo một tác vụ gửi email thông báo.
     * @param emailNotification: đối tượng email thông báo.
     * @param userAccount: tài khoản người dùng nhận thông báo.
     */
    public EmailNotificationTask(EmailNotification emailNotification, Account userAccount) {
        this.emailNotification = emailNotification;
        this.userAccount = userAccount;
    }


    public EmailNotificationTask(EmailNotification emailNotification) {
        this.emailNotification = emailNotification;
    }

    /**
     * Phương thức run khi thực hiện tác vụ gửi email thì tự động thực hiện vào một luồng riêng.
     */
    @Override
    public void run() {
        emailNotification.sendNotification(userAccount);
    }
}