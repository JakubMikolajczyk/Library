package com.Library.restAPI.service;

import com.Library.restAPI.model.Author;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Author getAuthorById(Long id);
    Page<Author> getAll(Pageable pageable);

    void createAuthor(Author author);
    void editAuthor(Author author);

    void deleteAuthor(Long id);
}
