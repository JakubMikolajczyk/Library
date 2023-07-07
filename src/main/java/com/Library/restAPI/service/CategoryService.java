package com.Library.restAPI.service;

import com.Library.restAPI.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getAllCategories();
    Category getCategoryById(Long id);

    void createCategory(Category category);
    void editCategory(Category category);
    void deleteCategoryById(Long id);

}
