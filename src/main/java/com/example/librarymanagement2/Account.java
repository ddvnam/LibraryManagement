package com.example.librarymanagement2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*Đây là lớp Accouunt dùng để quản lý tài khoản người dùng
 */
public class Account {
    /**
     * Đây là enum AccountStatus dùng để quản lý trạng thái của tài khoản
     * ACTIVE: tài khoản đang hoạt động
     * CANCELED: tài khoản đã bị hủy, không thể login khi trạng thái này
     * BLACKLISTED: tài khoản trả sách quá hạn nhiều lần
     * BLOCKED: tài khoản bị block trong một khoảng thời gian
     */
    public enum AccountStatus {
        ACTIVE, CANCELED, BLOCKED, NONE
    }

    private String username; // thay the cho id
    private String password;
    private AccountStatus status;
    private String role;
    private Person person;
    private String email;
    private ArrayList<PortalNotification> list_Portalnotice = new ArrayList<>();
    private int notificationID;

    //Constructors
    public Account() {
        this.username = "";
        this.password = "";
        this.status = AccountStatus.NONE;
        this.person = new Person();
        this.email = "";
        this.notificationID = 0;
    }

    public Account(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.status = AccountStatus.ACTIVE;
        this.email = email;
        this.role = role;
    }

    /**
     * Constructor
     * @param username
     * @param password
     * @param status
     *
     */

    public Account(String username, String password, String email ,AccountStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.email = email;
        this.notificationID = 0;
    }

    public Account(String username, String password, AccountStatus status, Person person) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.person = person;
        this.notificationID = 0;
    }

    //Getters and Setters

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

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //Da thay doi
    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public void addPortalNT(PortalNotification portal) { list_Portalnotice.add(portal); }

    public List<PortalNotification> getPortalNT() { return list_Portalnotice; }

    //Show toàn bộ thông báo chưa đọc trên portal
    public void showPortalNT() {
        System.out.println("CÁC THÔNG BÁO CHƯA ĐỌC:");
        for (int i = 0; i < list_Portalnotice.size(); i++) {
            if (!list_Portalnotice.get(i).isRead()) {
                System.out.println( (i + 1)  + " : " + list_Portalnotice.get(i).getCreatedDate() + " " + list_Portalnotice.get(i).getContent());
            }
        }
    }
    //Show thông báo theo ID người dùng muốn truy cập
    public void showPortalNTwithID (int ID){
        System.out.println(list_Portalnotice.get(ID - 1).getPortalNTwithID());
        list_Portalnotice.get(ID-1).setRead(true);
        list_Portalnotice.remove(ID - 1);
    }

    public void setNotificationID(int notificationID) { this.notificationID =  notificationID; }

    public int getNotificationID() { return this.notificationID; }

    /**
     * Gửi email thông báo cho người dùng.
     * @param content Nội dung thông báo.
     * @param subject Chủ đề thông báo (VD: THÔNG BÁO MƯỢN SÁCH, THÔNG BÁO TRẢ SÁCH).
     */
    public void sendEmailNotificationMember(String content, String subject) {
        EmailNotification notification = new EmailNotification(
                1, new Date(), content, subject
        );
        notification.sendNotification(this);// Gửi email tới tài khoản của Member
    }

    /**
     * Phương thức này dùng để kiểm tra thông tin đăng nhập
     * @param username
     * @param password
     * @return true nếu thông tin đăng nhập đúng, ngược lại trả về false đồng thời kiểm tra trạng thái tài khoản
     */
    public boolean validateLogin(String username, String password) {
        if(this.status == AccountStatus.CANCELED) {
            System.out.println("This account has been canceled. Please register a new account.");
            return false;
        }
        return this.username.equals(username) && this.password.equals(password);
    }
    public boolean resetPassword() {
        return true;
    }
}
