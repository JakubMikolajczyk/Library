package com.Library.restAPI.dto.request;


public record RegisterRequest(
        String username,
        String email,
        String name,
        String surname,
        String address,
        String city,
        String password
){ }
