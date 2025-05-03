package com.wipro.product_service.controller;

import com.wipro.product_service.dto.APIResponse;
import com.wipro.product_service.model.Category;
import com.wipro.product_service.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<APIResponse<?>> createCategory(
            @RequestBody Category category,
            HttpServletRequest request){
        try {
            Category categoryDTO = this.categoryService.createCategory(category, request);
            APIResponse<Category> apiResponse = new APIResponse<>(HttpStatus.CREATED, "Category created successfully.", categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    //TODO update and delete api

//    @PutMapping("/category/{categoryId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto , @PathVariable Long categoryId){
//        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto , categoryId);
//        return new ResponseEntity<>(updateCategory , HttpStatus.OK);
//    }
//
//    //delete - delete Category
//    @DeleteMapping("/category/{categoryId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
//        this.categoryService.deleteCategory(categoryId);
//        return new ResponseEntity<ApiResponse>( new ApiResponse("Category Deleted Successfully" , true) , HttpStatus.OK);
//    }

    @GetMapping("/retrieve/categories")
    public ResponseEntity<APIResponse<List<Category>>> getAllCategories() {
        List<Category> categoryDTOList = categoryService.getAllCategories();
        APIResponse<List<Category>> apiResponse = new APIResponse<>(HttpStatus.OK, categoryDTOList);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<?>> getCategory(@PathVariable String id) {
        try {
            Category categoryDTO = categoryService.getCategoryById(id);
            APIResponse<Category> apiResponse = new APIResponse<>(HttpStatus.OK, categoryDTO);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }
}