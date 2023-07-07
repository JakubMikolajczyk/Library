package com.Library.restAPI.mapper;

import com.Library.restAPI.dto.request.CategoryRequest;
import com.Library.restAPI.dto.response.CategoryDto;
import com.Library.restAPI.dto.response.Link;
import com.Library.restAPI.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .books(category.getBooks()
                        .stream()
                        .map(book -> new Link(book.getId(), "/api/v1/books/"))
                        .collect(Collectors.toList()))
                .build();
    }

    public Category toEntity(CategoryRequest categoryRequest){
        return Category.builder()
                .name(categoryRequest.name())
                .build();
    }

    public Category toEntity(Long id, CategoryRequest categoryRequest){
        Category category = toEntity(categoryRequest);
        category.setId(id);
        return category;
    }
}
