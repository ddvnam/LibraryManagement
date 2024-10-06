package com.example.librarymanagement2;

import java.util.Date;
import java.util.List;

public interface Search {
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> searchByPubDate(String publishDate);

}
