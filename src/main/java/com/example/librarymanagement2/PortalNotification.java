package com.example.librarymanagement2;

import java.util.Date;

public class PortalNotification extends Notification {
    private boolean isRead; // Trạng thái đọc thông báo

    // Constructor
    public PortalNotification(int notificationId, Date createdDate, String content) {
        super(notificationId, createdDate, content);
        this.isRead = false; // Mặc định chưa đọc
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getPortalNTwithID() { return this.getCreatedDate() + " " +this.getContent(); }

    // Override sendNotification để lưu thông báo vào Stack và hiển thị lên màn hình
    @Override
    public void sendNotification(Account userAccount) {}


}
