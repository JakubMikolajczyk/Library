package com.Library.restAPI.service.impl;

import com.Library.restAPI.exception.AuthorNotFoundException;
import com.Library.restAPI.model.Author;
import com.Library.restAPI.repository.AuthorRepository;
import com.Library.restAPI.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    @Override
    public Page<Author> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public void createAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void editAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
