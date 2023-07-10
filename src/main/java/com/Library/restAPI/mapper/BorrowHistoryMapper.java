package com.Library.restAPI.mapper;

import com.Library.restAPI.dto.response.BorrowHistoryDto;
import com.Library.restAPI.dto.response.Link;
import com.Library.restAPI.model.BorrowHistory;


public class BorrowHistoryMapper {

    public static BorrowHistoryDto toDto(BorrowHistory borrowHistory){
        return BorrowHistoryDto.builder()
                .id(borrowHistory.getId())
                .book(new Link(borrowHistory.getBook().getId(), "api/v1/books/"))
                .user(new Link(borrowHistory.getUser().getId(), "api/v1/users/"))
                .start(borrowHistory.getStartTime())
                .end(borrowHistory.getEndTime())
                .hidden(borrowHistory.isHidden())
                .build();
    }
}
