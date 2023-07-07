package com.Library.restAPI.dto.response;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record BookDto(
    Long id,
    String tittle,
    String ISBN,
    int year,
    Date deleteDate,
    Link author,
    List<Link> categories
) {}
