package com.example.librarymanagement2;

import java.util.Date;
import java.util.List;

public interface Search {
    List<BookItem> searchByTitle(String title);
    List<BookItem> searchByAuthor(String author);
    List<BookItem> searchBySubject(String subject);
    List<BookItem> searchByPubDate(Date publishDate);
}
