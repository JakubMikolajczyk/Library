package com.Library.restAPI.mapper;

import com.Library.restAPI.dto.request.SpecimenRequest;
import com.Library.restAPI.dto.response.Link;
import com.Library.restAPI.dto.response.SpecimenDto;
import com.Library.restAPI.model.SpecimenBorrow;
import com.Library.restAPI.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpecimenMapper {
    private final BookRepository bookRepository;

    public SpecimenDto toDto(SpecimenBorrow specimenBorrow){
        return SpecimenDto.builder()
                .id(specimenBorrow.getId())
                .book(new Link(specimenBorrow.getBook().getId(), "api/v1/books/"))
                .isBorrowed(specimenBorrow.getUser() != null)
                .build();
    }

    public SpecimenBorrow toEntity(SpecimenRequest specimenRequest){
        return SpecimenBorrow.builder()
                .book(bookRepository.getReferenceById(specimenRequest.bookId()))
                .build();
    }

    public SpecimenBorrow toEntity(Long bookId){
        return SpecimenBorrow.builder()
                .book(bookRepository.getReferenceById(bookId))
                .build();
    }
}
