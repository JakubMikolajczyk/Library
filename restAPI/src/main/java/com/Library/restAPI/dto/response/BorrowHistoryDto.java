package com.Library.restAPI.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record BorrowHistoryDto(
        Long id,
        Link book,
        Link user,
        Date start,
        Date end,
        boolean hidden
) {
}
