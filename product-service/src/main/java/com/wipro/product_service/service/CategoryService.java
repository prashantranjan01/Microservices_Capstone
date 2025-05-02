package com.wipro.product_service.service;

import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.model.Category;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category, HttpServletRequest request);

    Category getCategoryById(String id) throws ResourceNotFoundException;

    List<Category> getAllCategories();

//    CategoryDTO updateCategory(CategoryDTO categoryDTO , String id);
//
//    void deleteCategory(String id);
}