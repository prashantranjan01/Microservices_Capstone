package com.wipro.product_service.controller;

import com.wipro.product_service.dto.APIResponse;
import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.model.SubCategory;
import com.wipro.product_service.service.SubCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sub-category")
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<APIResponse<?>> createSubCategory(
            @RequestBody SubCategory subCategory,
            @PathVariable String categoryId,
            HttpServletRequest request){
        try {
            SubCategory subCategoryDTO = this.subCategoryService.createSubCategory(subCategory, categoryId, request);
            APIResponse<SubCategory> apiResponse = new APIResponse<>(HttpStatus.CREATED, "Subcategory created successfully.", subCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<APIResponse<?>> getSubCategoriesByCategoryId(@PathVariable String categoryId){
        try {
            List<SubCategory> subCategoryList = this.subCategoryService.getAllSubCategoriesByCategoryId(categoryId);
            APIResponse<List<SubCategory>> apiResponse = new APIResponse<>(HttpStatus.OK, subCategoryList);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @PutMapping
    public ResponseEntity<APIResponse<?>> updateSubCategory(
            @RequestBody SubCategory subCategory,
            HttpServletRequest request) {
        try {
            SubCategory updatedSubCategory = this.subCategoryService.updateSubCategory(subCategory, request);
            APIResponse<SubCategory> apiResponse = new APIResponse<>(HttpStatus.OK, "Subcategory updated successfully.", updatedSubCategory);
            return ResponseEntity.ok(apiResponse);
        } catch (ResourceNotFoundException e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (PermissionDeniedException e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.FORBIDDEN, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<APIResponse<?>> deleteSubCategory(
            @PathVariable String subCategoryId,
            HttpServletRequest request) {
        try {
            this.subCategoryService.deleteSubCategory(subCategoryId, request);
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.OK, "Subcategory deleted successfully.", null);
            return ResponseEntity.ok(apiResponse);
        } catch (ResourceNotFoundException e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (PermissionDeniedException e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.FORBIDDEN, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<APIResponse<?>> getSubCategoryById(@PathVariable String subCategoryId) {
        try {
            SubCategory subCategory = this.subCategoryService.getSubCategoryById(subCategoryId);
            APIResponse<SubCategory> apiResponse = new APIResponse<>(HttpStatus.OK, "Subcategory retrieved successfully.", subCategory);
            return ResponseEntity.ok(apiResponse);
        } catch (ResourceNotFoundException e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse<?>> getAllSubCategories() {
        try {
            List<SubCategory> subCategories = this.subCategoryService.getAllSubCategories();
            APIResponse<List<SubCategory>> apiResponse = new APIResponse<>(HttpStatus.OK, "All subcategories retrieved successfully.", subCategories);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
