# **Library Management System**

## **Overview**
Đây là một Hệ thống quản lý thư viện là một ứng dụng dựa trên Java được thiết kế để quản lý các hoạt động của thư viện như quản lý sách, thành viên, mượn và trả sách. Nó bao gồm các tính năng dành cho cả **thủ thư** và **thành viên**, cho phép xử lý hiệu quả các tác vụ của thư viện với giao diện người dùng đồ họa được xây dựng bằng JavaFX.

---

## **Features**
### For Librarians:
- Thêm, cập nhật hoặc xóa sách.
- Quản lý tài khoản thành viên.
- Xem lịch sử mượn.
- Sử dụng tiêu đề, tác giả hoặc danh mục để tìm kiếm sách.

### For Members:
- Tìm kiếm sách.
- Mượn và trả lại sách.
- Xem sách đã mượn.
- Kiểm tra tính khả dụng của sách.

---

## **Technologies Used**
- **Java**: Ngôn ngữ lập trình chính.
- **JavaFX**: Được sử dụng để xây dựng giao diện người dùng đồ họa.
- **JUnit**: Kiểm tra đơn vị cho ứng dụng.
- **MySQL**: Cơ sở dữ liệu được sử dụng cho dữ liệu kho lưu trữ.
- **Maven**: Xây dựng tự động hóa và quản lý phụ thuộc.
- **XAMPP** hoặc MySQL: Máy chủ cục bộ cho cơ sở dữ liệu MySQL.

---


---

## **Setup and Installation**
### Prerequisites:
1. **Java Development Kit (JDK)**: Phiên bản 11 hoặc cao hơn.

2. **Maven**: Quản lý các dependencies và build project.

3. **MySQL**: Cài đặt cơ sở dữ liệu.

4. **XAMPP** hoặc **MySQL Workbench**: Máy chủ MySQL từ phiên bản 9.0.


### Setup Steps
1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd <project_directory>
2. Cài đặt cơ sở dữ liệu:
   ```bash
   Mở WorkBench hoặc Xampp sau đó Import file .sql trong Folder java/Database.
3. Chạy file LiBraryAppUI.java trong IDE hoặc chạy lệnh :
   ```bash
   mvn javafx:run
4. Truy cập vào ứng dụng và bắt đầu sử dụng.


