package com.wipro.product_service.service;

import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.model.SubCategory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface SubCategoryService {
    SubCategory createSubCategory(SubCategory subCategory, String categoryId, HttpServletRequest request) throws ResourceNotFoundException, PermissionDeniedException;
    List<SubCategory> getAllSubCategoriesByCategoryId(String categoryId)throws ResourceNotFoundException;
}