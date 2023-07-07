package com.Library.restAPI.controller;

import com.Library.restAPI.dto.response.AuthorDto;
import com.Library.restAPI.dto.request.AuthorRequest;
import com.Library.restAPI.mapper.AuthorMapper;
import com.Library.restAPI.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping
    public Page<AuthorDto> getAllAuthors(Pageable pageable){
        return authorService.getAll(pageable).map(authorMapper::toDto);
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id){
        return authorMapper.toDto(authorService.getAuthorById(id));
    }
    
    @PostMapping
    public void createAuthor(@RequestBody AuthorRequest authorRequest){
        authorService.createAuthor(authorMapper.toEntity(authorRequest));
    }

    @PutMapping("/{id}")
    public void editAuthor(@PathVariable Long id, @RequestBody AuthorRequest authorRequest){
        authorService.editAuthor(authorMapper.toEntity(id, authorRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){//TODO if throw return reference books (links)
        authorService.deleteAuthor(id);
    }
}
