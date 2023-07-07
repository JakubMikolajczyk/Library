package com.Library.restAPI.dto.response;

import lombok.Builder;

@Builder
public record SpecimenDto(
        Long id,
        Link book,
        boolean isBorrowed
) {}
