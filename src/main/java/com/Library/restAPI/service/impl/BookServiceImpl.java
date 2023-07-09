package com.Library.restAPI.service.impl;

import com.Library.restAPI.exception.BookNotFoundException;
import com.Library.restAPI.model.Book;
import com.Library.restAPI.repository.BookRepository;
import com.Library.restAPI.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Override
    public Book getBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> getAllBooksWithoutDeleted(Pageable pageable) {
        return bookRepository.findAllByDeleteDateIsNull(pageable);
    }

    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void editBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
