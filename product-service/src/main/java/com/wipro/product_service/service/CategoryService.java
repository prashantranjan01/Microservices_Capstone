package com.wipro.product_service.service;

import com.wipro.product_service.dto.CategoryDTO;
import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.model.Category;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(Category category, HttpServletRequest request);

    CategoryDTO getCategoryById(String id) throws ResourceNotFoundException;

    List<CategoryDTO> getAllCategories();

//    CategoryDTO updateCategory(CategoryDTO categoryDTO , String id);
//
//    void deleteCategory(String id);
}