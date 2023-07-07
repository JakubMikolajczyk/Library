package com.Library.restAPI.dto.response;

import com.Library.restAPI.model.Role;
import lombok.Builder;

import java.util.Date;

@Builder
public record UserDto(
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
