CREATE DATABASE LibraryManagement;
USE LibraryManagement;

-- Tạo bảng Address
CREATE TABLE Address (
    addressID INT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(100),
    district VARCHAR(100)
);

-- Tạo bảng Person (dùng làm bảng cơ sở cho các lớp con như Author, Librarian, Member)
CREATE TABLE Person (
    personID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(100),
    phone VARCHAR(20),
    addressID INT,
    FOREIGN KEY (addressID) REFERENCES Address(addressID)
);

-- Tạo bảng Author (mở rộng từ Person)
CREATE TABLE Author (
    authorID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT
    # personID INT, vì trong lớp Author chỉ có thuộc tính name và description, không có các thuộc tính của person nên không biết nên thêm foreign key personId không
    # FOREIGN KEY (personID) REFERENCES Person(personID)
);

-- Tạo bảng Account
CREATE TABLE Account (
    accountID INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    email VARCHAR(255),
    # Thieu thuoc tinh ArrayList
    personID INT,
    memberID INT,
    accountStatusID INT,
    FOREIGN KEY (personID) REFERENCES Person(personID),
    FOREIGN KEY (memberID) REFERENCES Member(memberID),
    FOREIGN KEY (accountStatusID) REFERENCES AccountStatus(accountStatusID)
);

-- Bảng AccountPortalNotifications lưu danh sách thông báo của từng tài khoản
CREATE TABLE AccountPortalNotifications (
    accountID INT,
    portalNotificationID INT,
    PRIMARY KEY (accountID, portalNotificationID),
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (portalNotificationID) REFERENCES PortalNotification(potarlNotificationID)
);

-- Tạo bảng AccountStatus
CREATE TABLE AccountStatus (
    accountStatusID INT AUTO_INCREMENT PRIMARY KEY,
    statusName VARCHAR(50)
);

-- Tạo bảng Member (mở rộng từ Account)
CREATE TABLE Member (
    memberID INT AUTO_INCREMENT PRIMARY KEY,
    dateOfMembership DATE,
    totalBooksCheckedout INT,
    #Thieu list<BookItem>
    accountID INT,
    FOREIGN KEY (accountID) REFERENCES Account(accountID)
);

-- Tạo thuộc tính ArrayList<BookItem> borrowedBook
CREATE TABLE MemberborrowedBook (
    memberID INT,
    bookItemID INT,
    FOREIGN KEY (memberID) REFERENCES Member(memberID),
    FOREIGN KEY (bookItemID) REFERENCES BookItem(bookItemID)
);

-- Tạo bảng Librarian (mở rộng từ Person)
CREATE TABLE Librarian (
    librarianID INT AUTO_INCREMENT PRIMARY KEY,
    accountID INT,
    FOREIGN KEY (accountID) REFERENCES Account(accountID)
);

-- Tạo bảng Book
CREATE TABLE Book (
    bookID INT AUTO_INCREMENT PRIMARY KEY,
    ISBN VARCHAR(20),
    title VARCHAR(255),
    publisher VARCHAR(255),
    publicationDate INT,
    authorID INT,
    FOREIGN KEY (authorID) REFERENCES Author(authorID)
);

-- Tạo bảng BookItem (sách theo từng bản riêng)
CREATE TABLE BookItem (
    bookItemID INT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(50),
    dueDate DATE,
    price DOUBLE,
    dateOfPurchase DATE,
    numOfCopies INT,
    bookID INT,
    statusID INT,
    FOREIGN KEY (bookID) REFERENCES Book(bookID),
    FOREIGN KEY (statusID) REFERENCES BookStatus(statusID)
);

-- Tạo bảng BookStatus
CREATE TABLE BookStatus (
    statusID INT AUTO_INCREMENT PRIMARY KEY,
    statusName VARCHAR(50)
);

-- Bảng Catalog đại diện cho thông tin cơ bản của catalog
CREATE TABLE Catalog (
    catalogID INT AUTO_INCREMENT PRIMARY KEY,
    creationDate DATE,
    totalBooks INT
);

-- Bảng đại diện cho danh sách các BookItem trong Catalog
CREATE TABLE CatalogBookItems (
    catalogID INT,
    bookItemID INT,
    PRIMARY KEY (catalogID, bookItemID),
    FOREIGN KEY (catalogID) REFERENCES Catalog(catalogID),
    FOREIGN KEY (bookItemID) REFERENCES BookItem(bookItemID)
);

-- Bảng đại diện cho Map<String, List<Book>> theo tiêu đề (title) trong Catalog
CREATE TABLE CatalogBookTitles (
    catalogID INT,
    title VARCHAR(255),
    bookID INT,
    PRIMARY KEY (catalogID, title, bookID),
    FOREIGN KEY (catalogID) REFERENCES Catalog(catalogID),
    FOREIGN KEY (bookID) REFERENCES Book(bookID)
);

-- Bảng đại diện cho Map<String, List<Book>> theo tên tác giả (author) trong Catalog
CREATE TABLE CatalogBookAuthors (
    catalogID INT,
    authorName VARCHAR(255),
    bookID INT,
    PRIMARY KEY (catalogID, authorName, bookID),
    FOREIGN KEY (catalogID) REFERENCES Catalog(catalogID),
    FOREIGN KEY (bookID) REFERENCES Book(bookID)
);

-- Bảng đại diện cho Map<String, List<Book>> theo ngày xuất bản (publish date) trong Catalog
CREATE TABLE CatalogBookPublishDates (
    catalogID INT,
    publishDate INT,
    bookID INT,
    PRIMARY KEY (catalogID, publishDate, bookID),
    FOREIGN KEY (catalogID) REFERENCES Catalog(catalogID),
    FOREIGN KEY (bookID) REFERENCES Book(bookID)
);

-- Tạo bảng database
CREATE TABLE DatabaseConfig (
    configID INT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Bảng này để lưu thuộc tính currentAccount của LibraryApp
CREATE TABLE Session (
    sessionID INT AUTO_INCREMENT PRIMARY KEY,
    accountID INT,
    loginTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accountID) REFERENCES Account(accountID)
);

-- Tạo bảng Notification
CREATE TABLE Notification (
    notificationID INT AUTO_INCREMENT PRIMARY KEY,
    createdDate DATE,
    content TEXT
);

-- Tạo bảng EmailNotification
CREATE TABLE EmailNotification (
    emailNotificationID INT AUTO_INCREMENT PRIMARY KEY,
    subject TEXT,
    notificationID INT,
    FOREIGN KEY (notificationID) REFERENCES Notification(notificationID)
);

-- Tạo bảng PortalNotification
CREATE TABLE PortalNotification (
    potarlNotificationID INT AUTO_INCREMENT PRIMARY KEY,
    isRead boolean,
    notificationID INT,
    FOREIGN KEY (notificationID) REFERENCES Notification(notificationID)
);
