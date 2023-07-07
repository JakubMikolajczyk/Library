package com.Library.restAPI.repository;

import com.Library.restAPI.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findAllByDeleteDateIsNull();
    public Page<Book> findAllByDeleteDateIsNull(Pageable pageable);
}
