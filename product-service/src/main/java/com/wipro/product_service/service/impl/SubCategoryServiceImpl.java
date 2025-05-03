package com.wipro.product_service.service.impl;

import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.exception.ResourceServiceException;
import com.wipro.product_service.model.Category;
import com.wipro.product_service.model.SubCategory;
import com.wipro.product_service.model.SubCategoryAction;
import com.wipro.product_service.repository.CategoryRepository;
import com.wipro.product_service.repository.SubCategoryRepository;
import com.wipro.product_service.service.SubCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PermissionService permissionService;

    @Transactional
    @Override
    public SubCategory createSubCategory(SubCategory subCategory, String categoryId, HttpServletRequest request) throws ResourceNotFoundException, PermissionDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        if (!permissionService.hasPermission(request, String.valueOf(SubCategoryAction.CREATE_SUBCATEGORY))) {
            throw new PermissionDeniedException();
        }

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));;
        subCategory.setCategory(category);

        subCategory.setId(UUID.randomUUID().toString());
        subCategory.setCreatedAt(LocalDateTime.now());
        subCategory.setUpdatedAt(LocalDateTime.now());
        subCategory.setCreatedBy(userId);
        try {
            return this.subCategoryRepository.save(subCategory);
        } catch (Exception e) {
            throw new ResourceServiceException("Failed to create sub category.");
        }
    }
}