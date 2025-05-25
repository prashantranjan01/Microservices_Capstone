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
import java.util.List;
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

    @Override
    public List<SubCategory> getAllSubCategoriesByCategoryId(String categoryId) throws ResourceNotFoundException {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));;
        return this.subCategoryRepository.findByCategory(category);
    }

    @Transactional
    @Override
    public SubCategory updateSubCategory(SubCategory subCategory, HttpServletRequest request) throws ResourceNotFoundException, PermissionDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        if (!permissionService.hasPermission(request, String.valueOf(SubCategoryAction.UPDATE_SUBCATEGORY))) {
            throw new PermissionDeniedException();
        }

        SubCategory existingSubCategory = this.subCategoryRepository.findById(subCategory.getId())
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subCategory.getId()));

        existingSubCategory.setTitle(subCategory.getTitle());
        existingSubCategory.setUpdatedAt(LocalDateTime.now());

        try {
            return this.subCategoryRepository.save(existingSubCategory);
        } catch (Exception e) {
            throw new ResourceServiceException("Failed to update sub category.");
        }
    }

    @Transactional
    @Override
    public void deleteSubCategory(String subCategoryId, HttpServletRequest request) throws ResourceNotFoundException, PermissionDeniedException {
        if (!permissionService.hasPermission(request, String.valueOf(SubCategoryAction.DELETE_SUBCATEGORY))) {
            throw new PermissionDeniedException();
        }

        SubCategory subCategory = this.subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subCategoryId));

        try {
            this.subCategoryRepository.delete(subCategory);
        } catch (Exception e) {
            throw new ResourceServiceException("Failed to delete sub category.");
        }
    }

    @Override
    public SubCategory getSubCategoryById(String subCategoryId) throws ResourceNotFoundException {
        return this.subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id: " + subCategoryId));
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        return this.subCategoryRepository.findAll();
    }
}