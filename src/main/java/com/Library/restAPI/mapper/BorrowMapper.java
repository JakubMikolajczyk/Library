package com.Library.restAPI.mapper;

import com.Library.restAPI.dto.request.BorrowRequest;
import com.Library.restAPI.dto.request.BorrowUsernameRequest;
import com.Library.restAPI.dto.response.BorrowDto;
import com.Library.restAPI.dto.response.Link;
import com.Library.restAPI.exception.BorrowedException;
import com.Library.restAPI.exception.SpecimenNotFoundException;
import com.Library.restAPI.exception.WrongUsernameException;
import com.Library.restAPI.model.SpecimenBorrow;
import com.Library.restAPI.model.User;
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
        User userFromDb = userRepository.getReferenceById(borrowRequest.userId());
        return toEntity(borrowRequest.specimenId(), userFromDb);
    }

    public SpecimenBorrow toEntity(BorrowUsernameRequest borrowUsernameRequest){
        User userFromDb = userRepository.findByUsername(borrowUsernameRequest.username())
                .orElseThrow(WrongUsernameException::new);
        return toEntity(borrowUsernameRequest.specimenId(), userFromDb);
    }

    private SpecimenBorrow toEntity(Long specimenId, User user){
        SpecimenBorrow fromDb = specimenBorrowRepository.findById(specimenId)
                .orElseThrow(SpecimenNotFoundException::new);

        if (fromDb.getUser() != null)
            throw new BorrowedException();

        fromDb.setUser(user);
        fromDb.setStartTime(new Date());
        return fromDb;
    }
}
