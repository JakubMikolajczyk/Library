package com.Library.restAPI.service;

import com.Library.restAPI.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book getBookById(Long id);
    Page<Book> getAllBooks(Pageable pageable);

    Page<Book> getAllBooksWithoutDeleted(Pageable pageable);
    void createBook(Book book);
    void editBook(Book book);

    void deleteBookById(Long id);
}
