package com.example.librarymanagement2;

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
        ACTIVE, CANCELED, BLACKLISTED, BLOCKED, NONE
    }

    private String username; // thay the cho id
    private String password;
    private AccountStatus status;
    private String role;
    private Person person;

    //Constructors
    public Account() {
        this.username = "";
        this.password = "";
        this.status = AccountStatus.NONE;
        this.person = new Person();
    }

    /**
     * Constructor
     * @param username
     * @param password
     * @param status
     *
     */

    public Account(String username, String password, AccountStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public Account(String username, String password, AccountStatus status, Person person) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.person = person;
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
