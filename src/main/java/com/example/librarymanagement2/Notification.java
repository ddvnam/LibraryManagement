package com.example.librarymanagement2;

import java.util.Date;

public abstract class Notification {
    protected int notificationId;
    protected Date createdDate;
    protected String content;

    /**
     * Khoi tao mot thong bao
     * @param notificationId: id cua thong bao
     * @param createdDate: ngay tao thong bao
     * @param content : noi dung thong bao
     */
    // Constructor
    public Notification(int notificationId, Date createdDate, String content) {
        this.notificationId = notificationId;
        this.createdDate = createdDate;
        this.content = content;
    }

    public Notification(Date createdDate, String content) {
        this.createdDate = createdDate;
        this.content = content;
    }

    // Phương thức trừu tượng để gửi thông báo
    public abstract void sendNotification(Account userAccount);

    // Getter & Setter
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
