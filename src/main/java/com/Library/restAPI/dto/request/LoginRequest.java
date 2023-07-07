package com.Library.restAPI.dto.request;

public record LoginRequest(
        String username,
        String password
) { }
