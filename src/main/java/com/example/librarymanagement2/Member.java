package com.example.librarymanagement2;

import java.util.*;

public class Member extends Account{
    private Date dateOfMembership;
    private int totalBooksCheckedout;
    private List<BookItem> borrowedBooks;

    public Member(String username, String password, String email) {
        super(username, password, email ,AccountStatus.ACTIVE);
        this.setRole("member");
        this.dateOfMembership = new Date();
        borrowedBooks = new ArrayList<>();
    }

    public Member(String username) {
        this.setUsername(username);
        this.setRole("member");
    }
    public int getTotalBooksCheckedout() {
        return totalBooksCheckedout;
    }


    /**
     * Hàm này sẽ tìm kiếm sách trong Catalog dựa trên tiêu đề, tác giả hoặc năm xuất bản.
     * @return kết quả cần tìm
     */
    public void searchBooks(Catalog catalog) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select search option:");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Publication Date");
        int choice = scanner.nextInt();
        scanner.nextLine();
        List<Book> results = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                results = catalog.searchByTitle(title);
                break;
            case 2:
                System.out.print("Enter author name: ");
                String author = scanner.nextLine();
                results = catalog.searchByAuthor(author);
                break;
            case 3:
                System.out.print("Enter year: ");
                String year = scanner.nextLine();
                results = catalog.searchByPubDate(year);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        // Display search results
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Search Results:");
            for (Book book : results) {
                book.getInformation();
                System.out.println("-------------------------");
            }
        }
    }

    /**
     * Hàm này sẽ cho phép người dùng mượn sách từ Catalog.
     * @param catalog Catalog chứa thông tin sách
     * @return kết quả mượn sách
     */
    public void checkOutBook(Catalog catalog) {
        System.out.println("Please enter the book title you want to checkout: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        List<Book> books = catalog.searchByTitle(title);

        if (books.isEmpty()) {
            System.out.println("Book not found.");
            return;
        }

        for(Book book : books) {
            BookItem bookItem = catalog.getBookItem(book);
            if (bookItem == null) {
                System.out.println("Book not available.");
                return;
            }
            System.out.println("numOFcopies before checkout: " + bookItem.getCopies());
            if (bookItem.checkout()) {
                borrowedBooks.add(bookItem);

                //totalBooksCheckedout++;

                System.out.println("Book checked out successfully.");
                System.out.println("numOFcopies after checkout: " + bookItem.getCopies());

                //Gửi email lên portal
                PortalNotification portal = new PortalNotification(1, new Date(), "BẠN ĐÃ MƯỢN SÁCH THÀNH CÔNG: " + bookItem.getTitle());
                this.addPortalNT(portal);

                // Gửi email thông báo mượn sách
                if (!Objects.equals(this.getEmail(), "")) {
                    String content = "Bạn đã mượn sách thành công: " + bookItem.getTitle();
                    String subject = "THÔNG BÁO MƯỢN SÁCH";
                    this.sendEmailNotificationMember(content, subject);// Gửi email tới tài khoản của Member
                }

                return;
            }
        }
    }

    /**
     * Hàm này sẽ cho phép người dùng trả sách vào Catalog.
     * @param catalog Catalog chứa thông tin sách
     * @return kết quả trả sách
     */
    public void returnBook(Catalog catalog) {
        System.out.println("Please enter the book title you want to return: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        List<BookItem> returnedBooks = new ArrayList<>();

        // Find the book in the member's borrowed books list
        for(BookItem bookItem : borrowedBooks) {
            if (bookItem.getTitle().equals(title)) {
                returnedBooks.add(bookItem);
            }
        }

        // If the book was found in the member's borrowed list
        if (!returnedBooks.isEmpty()) {
            for(BookItem bookItem : returnedBooks) {
                // Remove the book from the member's borrowed list
                borrowedBooks.remove(bookItem);

                // Update the catalog to reflect the return
                BookItem catalogItem = catalog.getBookItem(bookItem);
                System.out.println("numOFcopies before return: " + catalogItem.getCopies());
                if (catalogItem != null) {
                    catalogItem.checkin(); // increase number of copies and change status
                    System.out.println("Book returned successfully.");
                    System.out.println("numOFcopies after return: " + catalogItem.getCopies());

                    //totalBookCheckout--;

                    //Gửi email về portal
                    PortalNotification portal = new PortalNotification(1, new Date(), "BẠN ĐÃ TRẢ SÁCH THÀNH CÔNG: " + bookItem.getTitle());
                    this.addPortalNT(portal);

                    // Gửi email thông báo trả sách
                    if(!Objects.equals(this.getEmail(), "")) {
                        String content = "Bạn đã trả sách thành công: " + bookItem.getTitle();
                        String subject = "THÔNG BÁO TRẢ SÁCH";
                        this.sendEmailNotificationMember(content, subject);  // Gửi email tới tài khoản của Member
                    }
                    return;
                } else {
                    System.out.println("Error: Could not find the book in the catalog.");
                }
            }
        } else {
            System.out.println("You have not borrowed this book.");
        }
    }

}
