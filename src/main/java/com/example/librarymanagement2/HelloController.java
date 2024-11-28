package com.example.librarymanagement2;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.util.Duration;
import static com.example.librarymanagement2.LibraryApp.currentAccount;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloController {
    @FXML
    private VBox BookBorrowDetails;

    @FXML
    private VBox ListBookBorrow;

    @FXML
    private VBox UIshowBook;

    @FXML
    private VBox UIshowListBook;

    @FXML
    private TextField searchField;

    @FXML
    private TextField searchFieldAuthor;

    @FXML
    private TextField searchOnShelf;

    @FXML
    private VBox notificationBox;

    @FXML
    private VBox VBox_addAuthor;

    @FXML
    private VBox UIshowAuthor;

    @FXML
    private VBox UIshowListAuthor;

    @FXML
    private void showNotification() {
        notificationBox.setVisible(true);
    }

    @FXML
    private void hideNotification() {
        notificationBox.setVisible(false);
    }

    private int currentRow = 0;
    private int currentCol = 0;
    private final int ITEMS_PER_LOAD = 6;
    private BookRepository bookRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);
    private final String[] colors = {"#B9E5FF", "#ADDCC8", "#F88F3C", "#BDB2FE", "#90de99", "#FB9AA8","#F6DB68", "#FF5056", "#F9CDAD"};

    @FXML
    private GridPane gridPane;

    @FXML
    private void showDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Thông báo");
        dialog.setContentText("Ấn cái cc gì?!");
        ButtonType closeButton = new ButtonType("Đóng", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.show();
    }

    @FXML
    private void onSearch() {
        String searchQuery = searchField.getText().trim();

        gridPane.getChildren().clear();

        currentRow = 0;
        currentCol = 0;

        if (!searchQuery.isEmpty()) {
            List<BookItem> searchResults = bookRepository.searchBooks(searchQuery);
            if (searchResults.isEmpty()) {
                System.out.println("Empty search query");
                Label noResultsLabel = new Label("No books found.");
                noResultsLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #0077ff; -fx-font-weight: bold");
                gridPane.add(noResultsLabel, 0, 0);
            } else {
                updateSearchResults(searchResults);
            }
        } else {
            loadBooksInChunks();
        }
    }

    private void updateSearchResults(List<BookItem> searchResults) {
        gridPane.getChildren().clear();
        currentRow = 0;
        currentCol = 0;

        // Lấy tối đa 20 kết quả từ danh sách tìm kiếm
        List<BookItem> limitedResults = searchResults.size() > 20
                ? searchResults.subList(0, 20)
                : searchResults;

        int[] currentIndex = {0};

        // Task để tải từng cuốn sách
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() {
                while (currentIndex[0] < limitedResults.size()) {
                    int index = currentIndex[0];
                    currentIndex[0]++;

                    Platform.runLater(() -> {
                        BookItem bookItem = limitedResults.get(index);
                        VBox bookBox = createBookBox(bookItem,HelloController.this::back,UIshowListBook,UIshowBook);

                        gridPane.add(bookBox, currentCol, currentRow);
                        currentCol++;

                        if (currentCol == 4) {
                            currentCol = 0;
                            currentRow++;
                        }
                    });

                    try {
                        Thread.sleep(100); // Nghỉ một chút để tránh lag
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return null;
            }
        };

        Button loadMoreButton = new Button("Load More");
        loadMoreButton.setOnAction(event -> {
            int nextIndex = currentIndex[0];
            List<BookItem> nextBatch = searchResults.subList(nextIndex, Math.max(nextIndex + ITEMS_PER_LOAD, searchResults.size()));
            updateSearchResults(nextBatch);
        });
        Thread backgroundThread = new Thread(loadTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
        gridPane.add(loadMoreButton, 0, currentRow++);
    }

    private void updateSearchBookResults(List<BookItem> searchResults) {
        List<BookItem> limitedResults = searchResults.size() > 20
                ? searchResults.subList(0, 20)
                : searchResults;
        int currentIndex = 0;
        while(currentIndex<limitedResults.size()) {
            int index = currentIndex;
            currentIndex++;
            BookItem bookItem = limitedResults.get(index);
            VBox cr;
        }
    }

    @FXML
    private void onSearchAuthor() {
        String searchQuery = searchFieldAuthor.getText().trim();

        VBox_addAuthor.getChildren().clear();

        if (!searchQuery.isEmpty()) {
            List<BookItem> searchResults = bookRepository.searchAuthors(searchQuery);
            if (searchResults.isEmpty()) {
                System.out.println("Empty search query");
                Label noResultsLabel = new Label("No books found.");
                noResultsLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #0077ff; -fx-font-weight: bold");
                VBox_addAuthor.getChildren().add(noResultsLabel);
            } else {
                updateSearchAuthorResults(searchResults);
            }
        } else {
            updateBookAuthorSequentially();
        }
    }

    private void updateSearchAuthorResults(List<BookItem> searchResults) {
        VBox_addAuthor.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        VBox resultContainer = new VBox(10); // Container cho kết quả
        resultContainer.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(resultContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        VBox_addAuthor.getChildren().add(scrollPane);

        // Giới hạn kết quả nếu cần thiết
        List<BookItem> limitedResults = searchResults.size() > 20
                ? searchResults.subList(0, 20)
                : searchResults;

        int[] currentIndex = {0};

        // Task để tải từng kết quả
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() {
                while (currentIndex[0] < limitedResults.size()) {
                    int index = currentIndex[0];
                    currentIndex[0]++;

                    Platform.runLater(() -> {
                        BookItem bookItem = limitedResults.get(index);

                        // Tạo VBox đại diện cho mỗi kết quả
                        VBox bookBox = createBookBox(bookItem,HelloController.this::back2,UIshowListAuthor,UIshowAuthor);

                        // Thêm vào resultContainer
                        resultContainer.getChildren().add(bookBox);
                    });

                    try {
                        Thread.sleep(150); // Nghỉ để tránh lag
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return null;
            }
        };
        Button loadMoreButton = new Button("Load More");
        loadMoreButton.setOnAction(event -> {
            int nextIndex = currentIndex[0];
            List<BookItem> nextBatch = searchResults.subList(nextIndex, Math.max(nextIndex + ITEMS_PER_LOAD, searchResults.size()));
            updateSearchResults(nextBatch);
        });
        Thread backgroundThread = new Thread(loadTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
//        gridPane.add(loadMoreButton, 0, currentRow++);
    }

    @FXML
    private void onSearchOnBooksShelf() {
        String searchQuery = searchOnShelf.getText().trim();
        ListBookBorrow.getChildren().clear();
        if(!searchQuery.isEmpty()) {
            List<BookItem> searchResults = bookRepository.searchBorrowedBooks(searchQuery);
            if (searchResults.isEmpty()) {
                System.out.println("Empty search query");
                Label noResultsLabel = new Label("No books found.");
                noResultsLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #0077ff; -fx-font-weight: bold");
                ListBookBorrow.getChildren().add(noResultsLabel);
            }
            else {
                for (BookItem bookItem : searchResults) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("cardBorrow.fxml"));
                        HBox cardBox = fxmlLoader.load();

                        CardBorrowBookController cardBorrowBook = fxmlLoader.getController();
                        cardBorrowBook.bookBorrowed_setData(bookItem);

                        BookItem currentBookItem = bookItem;
                        cardBox.setOnMouseClicked(event -> showBookDetailsBeside(currentBookItem));
                        ListBookBorrow.getChildren().add(cardBox);
                        if(BookBorrowDetails.getChildren().isEmpty()) {
                            Label no_details_Label = new Label("NO DETAILS");
                            BookBorrowDetails.getChildren().add(no_details_Label );
                        }
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                }
            }
        } else {
            loadBorrowedBook();
        }

    }

    public void initialize() {
        bookRepository = new BookRepository();
        if(VBox_addAuthor != null) {
            updateBookAuthorSequentially();
        }

        if(gridPane != null)
        {
            Platform.runLater(this::loadBooksInChunks);
        }

        if(ListBookBorrow!=null)
        {
            System.out.println("ListBookBorrow");
            loadBorrowedBook();
        }
    }

    private void loadBooksInChunks() {
        List<BookItem> allBooks = bookRepository.getBookItems();
        int totalBooks = allBooks.size();
        int[] currentIndex = {0};

        // Task để tải dữ liệu dần dần
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() {
                while (currentIndex[0] < totalBooks) {
                    int start = currentIndex[0];
                    int end = Math.min(start + ITEMS_PER_LOAD, totalBooks);

                    // Lấy nhóm sách hiện tại
                    List<BookItem> bookChunk = allBooks.subList(start, end);
                    currentIndex[0] = end;

                    // Cập nhật giao diện
                    Platform.runLater(() -> updateGrid(bookChunk));

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return null;
            }
        };
        executorService.submit(loadTask);
    }

    private void updateGrid(List<BookItem> bookItems) {
        for (BookItem bookItem : bookItems) {
            VBox bookBox = createBookBox(bookItem,this::back,UIshowListBook,UIshowBook);

            gridPane.add(bookBox, currentCol, currentRow);
            currentCol++;

            if (currentCol == 4) {
                currentCol = 0;
                currentRow++;
            }
        }
    }

    private VBox createBookBox(BookItem bookItem,EventHandler<ActionEvent> backActionHandler,VBox ListBook, VBox BookDetails) {
        Image image;
        try {
            String URL = bookItem.getISBN();
            image = ImageLoader.loadImageFromFile(URL);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            image = new Image("file:/path/to/default-image.png");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(140);
        imageView.setFitHeight(150);

        Label nameLabel = new Label(bookItem.getTitle());
        nameLabel.getStyleClass().add("bookTitle");
        Label priceLabel = new Label("$" + bookItem.getPrice());
        priceLabel.getStyleClass().add("bookPrice");

        VBox bookBox = new VBox(imageView, nameLabel, priceLabel);
        bookBox.getStyleClass().add("book-display-box");
        String randomColor = colors[(int) (Math.random() * colors.length)];
        bookBox.setStyle("-fx-background-color: " + randomColor);
        bookBox.setAlignment(Pos.CENTER);
        bookBox.setSpacing(8);

        bookBox.setOnMouseClicked(event -> showBookDetails(bookItem,backActionHandler,ListBook,BookDetails));

        return bookBox;
    }

    private void updateBookAuthorSequentially() {
        List<String> authorNames = bookRepository.Author_inf();
        VBox_addAuthor.getChildren().clear();
        loadNextAuthor(0, authorNames);
    }

    private void loadNextAuthor(int authorIndex, List<String> authorNames) {
        if (authorIndex >= authorNames.size()) {
            return;
        }

        String authorName = authorNames.get(authorIndex);

        Text authorText = new Text(authorName);
        authorText.getStyleClass().add("author-name");

        HBox booksHBox = new HBox();
        booksHBox.setSpacing(10);
        booksHBox.getStyleClass().add("books-hbox");
        booksHBox.setAlignment(Pos.CENTER);
        booksHBox.setSpacing(10);

        Label loadingLabel = new Label("Loading...");
        booksHBox.getChildren().add(loadingLabel);

        ScrollPane scrollPane = new ScrollPane(booksHBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("author-scroll-pane");

        VBox authorBox = new VBox(authorText, scrollPane);
        authorBox.setSpacing(10);
        authorBox.getStyleClass().add("author-box");

        VBox_addAuthor.getChildren().add(authorBox);

        Task<Void> loadAuthorTask = new Task<>() {
            @Override
            protected Void call() {
                List<BookItem> booksByAuthor = bookRepository.getBooks_byAuthor(authorName);

                Platform.runLater(() -> {
                    booksHBox.getChildren().clear();
                    loadBooksByChunks(booksHBox, booksByAuthor, () -> loadNextAuthor(authorIndex + 1, authorNames));
                });

                return null;
            }
        };

        executorService.submit(loadAuthorTask);
    }

    private void loadBooksByChunks(HBox hBox, List<BookItem> booksByAuthor, Runnable onComplete) {
        int ITEMS_PER_LOAD = 5;
        int[] currentIndex = {0};

        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (currentIndex[0] < booksByAuthor.size()) {
                    int start = currentIndex[0];
                    int end = Math.min(start + ITEMS_PER_LOAD, booksByAuthor.size());
                    List<BookItem> bookChunk = booksByAuthor.subList(start, end);
                    currentIndex[0] = end;

                    // Cập nhật giao diện trên JavaFX Application Thread
                    Platform.runLater(() -> {
                        for (BookItem bookItem : bookChunk) {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                                HBox cardBox = fxmlLoader.load();

                                CardAuthorController cardAuthor = fxmlLoader.getController();
                                cardAuthor.bookAuthor_setData(bookItem);
                                cardBox.setOnMouseClicked(event -> showBookDetails(bookItem,HelloController.this::back2,UIshowListAuthor,UIshowAuthor));
                                hBox.getChildren().add(cardBox);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    // Dừng lại 50ms trước khi tải phần tiếp theo
                    Thread.sleep(50);
                }

                // Thực thi hành động hoàn thành khi tất cả sách đã được tải
                Platform.runLater(onComplete);

                return null;
            }
        };
        executorService.submit(loadTask);
    }

    private void loadBorrowedBook() {
        List<BookItem> ListBorrowedBook = bookRepository.bookBorrowed();
        if(ListBorrowedBook.size() <= 0) {
            Label loadingLabel = new Label("You have not borrowed any book");
            loadingLabel.getStyleClass().add("loading-label");
        }
        else {
            for (BookItem bookItem : ListBorrowedBook) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("cardBorrow.fxml"));
                    HBox cardBox = fxmlLoader.load();

                    CardBorrowBookController cardBorrowBook = fxmlLoader.getController();
                    cardBorrowBook.bookBorrowed_setData(bookItem);

                    BookItem currentBookItem = bookItem;
                    cardBox.setOnMouseClicked(event -> showBookDetailsBeside(currentBookItem));
                    ListBookBorrow.getChildren().add(cardBox);
                    if(BookBorrowDetails.getChildren().isEmpty()) {
                        Label no_details_Label = new Label("NO DETAILS");
                        BookBorrowDetails.getChildren().add(no_details_Label );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showBookDetailsBeside(BookItem bookItem) {
        try {
            System.out.println("details Beside Shown");
            BookBorrowDetails.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("BookBorrowDetails.fxml"));
            VBox detailsShown = fxmlLoader.load();

            BookBorrowDetailsController bookBorrowDetailsController = fxmlLoader.getController();
            bookBorrowDetailsController.setDataToShow(bookItem);

            BookBorrowDetails.getChildren().add(detailsShown);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void back(ActionEvent event) {
        UIshowListBook.setVisible(true);
        UIshowBook.setVisible(false);
    }

    private void back2(ActionEvent event) {
        UIshowAuthor.setVisible(false);
        UIshowListAuthor.setVisible(true);
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    private void showBookDetails(BookItem bookItem, EventHandler<ActionEvent> backActionHandler,VBox ListBook, VBox BookDetails) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("bookDetails.fxml"));
            VBox showBook = fxmlLoader.load();

            BookDetailsController controller = fxmlLoader.getController();
            controller.setBookDetails(bookItem,backActionHandler);
            BookDetails.getChildren().clear();
            BookDetails.getChildren().add(new VBox(showBook));

            ListBook.setVisible(false);
            BookDetails.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void home(MouseEvent event) throws IOException {
        if( !executorService.isShutdown() && !executorService.isTerminated()) {
            executorService.shutdownNow();
        }
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = stage.getScene();

        scene.setRoot(root);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void author(MouseEvent event) throws IOException {
        if( !executorService.isShutdown() && !executorService.isTerminated()) {
            executorService.shutdownNow();
        }
        root = FXMLLoader.load(getClass().getResource("author.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = stage.getScene();

        scene.setRoot(root);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void myBookShelf(MouseEvent event) throws IOException {
        if( !executorService.isShutdown() && !executorService.isTerminated()) {
            executorService.shutdownNow();
        }
        root = FXMLLoader.load(getClass().getResource("myBookShelf.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = stage.getScene();

        scene.setRoot(root);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        stage.setMaximized(true);
        stage.show();
    }

}