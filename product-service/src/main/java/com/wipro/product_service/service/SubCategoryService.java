package com.wipro.product_service.service;

import com.wipro.product_service.dto.SubCategoryDTO;
import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.model.SubCategory;
import jakarta.servlet.http.HttpServletRequest;


public interface SubCategoryService {
    SubCategoryDTO createSubCategory(SubCategory subCategory, String categoryId, HttpServletRequest request) throws ResourceNotFoundException, PermissionDeniedException;
}