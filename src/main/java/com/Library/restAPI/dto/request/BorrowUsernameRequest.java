package com.Library.restAPI.dto.request;

public record BorrowUsernameRequest (
        Long specimenId,
        String username
){}
