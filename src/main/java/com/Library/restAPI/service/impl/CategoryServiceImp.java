package com.Library.restAPI.service.impl;

import com.Library.restAPI.exception.CategoryNotFoundException;
import com.Library.restAPI.model.Category;
import com.Library.restAPI.repository.CategoryRepository;
import com.Library.restAPI.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void editCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
