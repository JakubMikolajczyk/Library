package com.Library.restAPI.mapper;

import com.Library.restAPI.dto.request.BorrowRequest;
import com.Library.restAPI.dto.response.BorrowDto;
import com.Library.restAPI.dto.response.Link;
import com.Library.restAPI.model.SpecimenBorrow;
import com.Library.restAPI.repository.SpecimenBorrowRepository;
import com.Library.restAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class BorrowMapper {

    private final UserRepository userRepository;
    private final SpecimenBorrowRepository specimenBorrowRepository;

    public BorrowDto toDto(SpecimenBorrow specimenBorrow){
        return BorrowDto.builder()
                .id(specimenBorrow.getId())
                .book(new Link(specimenBorrow.getBook().getId(), "api/v1/books/"))
                .user(new Link(specimenBorrow.getUser().getId(), "api/v1/users/"))
                .start(specimenBorrow.getStartTime())
                .build();
    }

    public SpecimenBorrow toEntity(BorrowRequest borrowRequest){
        SpecimenBorrow fromDb = specimenBorrowRepository.findByIdAndUserIsNull(borrowRequest.specimenId())
                .orElseThrow(RuntimeException::new); //TODO exception (specimen not exist or specimen is borrowed)

        fromDb.setUser(userRepository.getReferenceById(borrowRequest.userId()));
        fromDb.setStartTime(new Date());
        return fromDb;
    }
}
