package com.Library.restAPI.dto.response;

import lombok.Builder;

@Builder
public record AuthorDto (
        Long id,
        String name,
        String surname
        //Add link to related books?
){}
