package com.Library.restAPI.controller;

import com.Library.restAPI.dto.request.CategoryRequest;
import com.Library.restAPI.dto.response.CategoryDto;
import com.Library.restAPI.mapper.CategoryMapper;
import com.Library.restAPI.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return categoryService.getAllCategories()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void createCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createCategory(categoryMapper.toEntity(categoryRequest));
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable Long id){
        return categoryMapper.toDto(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public void editCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        categoryService.editCategory(categoryMapper.toEntity(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }
}
