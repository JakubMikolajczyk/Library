package com.Library.restAPI.dto.request;

public record UserEditRequest (
    String email,
    String name,
    String surname,
    String address,
    String city
){}
