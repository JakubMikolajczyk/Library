package com.Library.restAPI.dto.request;

public record PasswordChangeRequest (
        String oldPassword,
        String newPassword
){
}
