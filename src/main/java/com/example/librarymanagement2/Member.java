package com.example.librarymanagement2;

import java.util.*;

class Member extends Account {
    private Random random = new Random();
    private double balance;
    private Date dateOfMembership;
    private int totalBooksCheckedout;
    private List<BookItem> borrowedBooks;
    private TransactionService transactionService;

    public Member(String username, String password, String email, TransactionService transactionService) {
        super(username, password, email, AccountStatus.ACTIVE);
        this.setRole("member");
        this.dateOfMembership = new Date();
        borrowedBooks = new ArrayList<>();
        this.balance = random.nextDouble() * 100; // Set balance between 0 and 100
        this.transactionService = transactionService;
    }

    public Member(String username, TransactionService transactionService) {
        this.setUsername(username);
        this.setRole("member");
        this.transactionService = transactionService;
        borrowedBooks = new ArrayList<>();
    }

    public Member(String username, String password, String mail) {
        super(username, password, mail, AccountStatus.ACTIVE);
        borrowedBooks = new ArrayList<>();
        transactionService = new TransactionService();
    }


    public int getTotalBooksCheckedout() {
        return totalBooksCheckedout;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

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

    public void checkOutBook(Catalog catalog) {
        System.out.println("Please enter the book title you want to checkout: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        List<Book> books = catalog.searchByTitle(title);

        if (books.isEmpty()) {
            System.out.println("Book not found.");
            return;
        }

        for (Book book : books) {
            BookItem bookItem = catalog.getBookItem(book);
            if (bookItem == null) {
                System.out.println("Book not available.");
                return;
            }
            System.out.println("numOFcopies before checkout: " + bookItem.getNumOfCopies());

            if (this.balance < bookItem.getPrice()) {
                System.out.println("You do not have enough money!");
            } else if (bookItem.checkout()) {
                borrowedBooks.add(bookItem);
                this.setBalance(this.getBalance() - bookItem.getPrice());
                book.getInformation();

                transactionService.addTransaction(this.getUsername(), bookItem.getISBN(), "BORROW");

                System.out.println("Book checked out successfully.");
                System.out.println("numOFcopies after checkout: " + bookItem.getNumOfCopies());

                PortalNotification portal = new PortalNotification(1, new Date(), "BẠN ĐÃ MƯỢN SÁCH THÀNH CÔNG: " + bookItem.getTitle());
                this.addPortalNT(portal);

                if (!Objects.equals(this.getEmail(), "")) {
                    String content = "Bạn đã mượn sách thành công: " + bookItem.getTitle();
                    String subject = "THÔNG BÁO MƯỢN SÁCH";
                    this.sendEmailNotificationMember(content, subject);
                }
                return;
            }
        }
    }

    public void returnBook(Catalog catalog) {
        System.out.println("Please enter the book title you want to return: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        List<BookItem> returnedBooks = new ArrayList<>();

        for (BookItem bookItem : borrowedBooks) {
            if (bookItem.getTitle().equals(title)) {
                returnedBooks.add(bookItem);
            }
        }

        if (!returnedBooks.isEmpty()) {
            for (BookItem bookItem : returnedBooks) {
                borrowedBooks.remove(bookItem);
                BookItem catalogItem = catalog.getBookItem(bookItem);
                System.out.println("numOFcopies before return: " + catalogItem.getNumOfCopies());
                if (catalogItem != null) {
                    catalogItem.checkin();
                    System.out.println("Book returned successfully.");
                    System.out.println("numOFcopies after return: " + catalogItem.getNumOfCopies());

                    transactionService.addTransaction(this.getUsername(), bookItem.getISBN(), "RETURN");

                    PortalNotification portal = new PortalNotification(1, new Date(), "BẠN ĐÃ TRẢ SÁCH THÀNH CÔNG: " + bookItem.getTitle());
                    this.addPortalNT(portal);

                    if (!Objects.equals(this.getEmail(), "")) {
                        String content = "Bạn đã trả sách thành công: " + bookItem.getTitle();
                        String subject = "THÔNG BÁO TRẢ SÁCH";
                        this.sendEmailNotificationMember(content, subject);
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