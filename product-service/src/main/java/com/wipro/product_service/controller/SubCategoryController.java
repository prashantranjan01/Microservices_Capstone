package com.wipro.product_service.controller;

import com.wipro.product_service.dto.APIResponse;
import com.wipro.product_service.dto.SubCategoryDTO;
import com.wipro.product_service.model.SubCategory;
import com.wipro.product_service.service.SubCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @PostMapping("/category/{categoryId}/sub-category")
    public ResponseEntity<APIResponse<?>> createSubCategory(
            @RequestBody SubCategory subCategory,
            @PathVariable String categoryId,
            HttpServletRequest request){
        try {
            SubCategoryDTO subCategoryDTO = this.subCategoryService.createSubCategory(subCategory,categoryId, request);
            APIResponse<SubCategoryDTO> apiResponse = new APIResponse<>(HttpStatus.CREATED, "Subcategory created successfully.", subCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
