package com.Library.restAPI.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record BorrowDto(
        Long id,
        Link book,
        Link user,
        Date start
) {
}
