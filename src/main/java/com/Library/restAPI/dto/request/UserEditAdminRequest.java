package com.Library.restAPI.dto.request;

import com.Library.restAPI.model.Role;

import java.util.Date;

public record UserEditAdminRequest(
        String username,
        String email,
        String name,
        String surname,
        String address,
        String city,
        Role role,
        Date deleteDate
) {
}
