package com.Library.restAPI.controller;

import com.Library.restAPI.dto.response.BookDto;
import com.Library.restAPI.dto.request.BookRequest;
import com.Library.restAPI.dto.response.SpecimenDto;
import com.Library.restAPI.mapper.BookMapper;
import com.Library.restAPI.mapper.SpecimenMapper;
import com.Library.restAPI.service.BookService;
import com.Library.restAPI.service.SpecimenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;
    private final SpecimenService specimenService;
    private final BookMapper bookMapper;
    private final SpecimenMapper specimenMapper;


    @GetMapping("")
    public Page<BookDto> getAllBooks(Pageable pageable){
        return bookService.getAllBooks(pageable)
                .map(bookMapper::toDto);
    }

    @PostMapping()
    public void createBook(@RequestBody BookRequest bookRequest){
        bookService.createBook(bookMapper.toEntity(bookRequest));
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id){
        return bookMapper.toDto(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public void editBookById(@PathVariable Long id, @RequestBody BookRequest bookRequest){
        bookService.editBook(bookMapper.toEntity(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }

    @GetMapping("/{bookId}/specimens")
    public List<SpecimenDto> getSpecimenByBookId(@PathVariable Long bookId){
        return specimenService
                .getSpecimenByBookId(bookId)
                .stream()
                .map(specimenMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{bookId}/specimens")
    public void createSpecimenByBookId(@PathVariable Long bookId){
        specimenService.createSpecimen(specimenMapper.toEntity(bookId));
    }

}
