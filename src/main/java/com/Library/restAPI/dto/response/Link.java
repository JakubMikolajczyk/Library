package com.Library.restAPI.dto.response;

public record Link(Long id, String href) {
    public Link(Long id, String href) {
        this.id = id;
        this.href = href + id.toString();
    }
}