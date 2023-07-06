package com.Library.restAPI.dto.request;

public record BorrowRequest(
        Long specimenId,
        Long userId
) { }
