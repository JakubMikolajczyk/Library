package com.Library.restAPI.controller;

import com.Library.restAPI.dto.response.BookDto;
import com.Library.restAPI.dto.request.BookRequest;
import com.Library.restAPI.dto.response.BorrowDto;
import com.Library.restAPI.dto.response.BorrowHistoryDto;
import com.Library.restAPI.dto.response.SpecimenDto;
import com.Library.restAPI.mapper.BookMapper;
import com.Library.restAPI.mapper.BorrowHistoryMapper;
import com.Library.restAPI.mapper.BorrowMapper;
import com.Library.restAPI.mapper.SpecimenMapper;
import com.Library.restAPI.service.BookService;
import com.Library.restAPI.service.BorrowHistoryService;
import com.Library.restAPI.service.BorrowService;
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
    private final BorrowService borrowService;
    private final BorrowHistoryService borrowHistoryService;
    private final BookMapper bookMapper;
    private final SpecimenMapper specimenMapper;
    private final BorrowMapper borrowMapper;

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

    @GetMapping("/{bookId}/borrows")
    public List<BorrowDto> getBorrowsByBookId(@PathVariable Long bookId){
        return borrowService
                .getAllBorrowsByBookId(bookId)
                .stream()
                .map(borrowMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{bookId}/borrowHistories")
    public List<BorrowHistoryDto> getHistoryByBookId(@PathVariable Long bookId){
        return borrowHistoryService
                .getAllHistoryByBookId(bookId)
                .stream()
                .map(BorrowHistoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
