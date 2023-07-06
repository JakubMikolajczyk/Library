package com.Library.restAPI.dto.request;

import java.util.Date;
import java.util.List;

public record BookRequest (
        String tittle,
        String ISBN,
        int year,
        Date deleteDate,
        Long authorId,
        List<Long> categoriesId
){}
