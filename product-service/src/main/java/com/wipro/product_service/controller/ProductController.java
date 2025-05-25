package com.wipro.product_service.controller;

import com.wipro.product_service.dto.APIResponse;
import com.wipro.product_service.model.Category;
import com.wipro.product_service.model.Product;
import com.wipro.product_service.model.ProductStatus;
import com.wipro.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/retrieve/products")
    public ResponseEntity<APIResponse<List<Product>>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        APIResponse<List<Product>> listAPIResponse = new APIResponse<>(HttpStatus.OK , productList);
        return ResponseEntity.ok(listAPIResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<?>> getProductById(@PathVariable String id) {
        try {
            Product product = productService.getProductById(id);
            APIResponse<Product> apiResponse = new APIResponse<>(HttpStatus.OK , product);
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Product>>> getProductByStatus(@RequestParam ProductStatus status) {
        List<Product> productList = productService.getAllProductsByStatus(status);
        APIResponse<List<Product>> listAPIResponse = new APIResponse<>(HttpStatus.OK , productList);
        return ResponseEntity.ok(listAPIResponse);
    }

    @PostMapping("/category/{categoryId}/sub-category/{subCategoryId}")
    public ResponseEntity<APIResponse<?>> createProduct(
            @RequestBody Product product,
            @PathVariable String categoryId,
            @PathVariable String subCategoryId,
            HttpServletRequest request) {
        try {
            Product createdProduct = productService.createProduct(product,categoryId , subCategoryId, request);
            APIResponse<Product> apiResponse = new APIResponse<>(HttpStatus.CREATED, "Product created successfully.", createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/category/{categoryId}/sub-category/{subCategoryId}")
    public ResponseEntity<APIResponse<?>> getProductsByCategoryIdAndSubCategoryId(
            @PathVariable String categoryId,
            @PathVariable String subCategoryId) {
        try {
            List<Product> productList = productService.getProductsByCategoryIdAndSubCategoryId(categoryId , subCategoryId);
            APIResponse<List<Product>> apiResponse = new APIResponse<>(HttpStatus.OK,  productList);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

    @GetMapping("/s/products")
    public ResponseEntity<APIResponse<?>> getProductsByQuery(
            @RequestParam String q){
        try {
            List<Product> productList = productService.getProductsByQuery(q);
            APIResponse<List<Product>> apiResponse = new APIResponse<>(HttpStatus.OK,  productList);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<?>> updateProduct(
            @RequestBody Product product,
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            Product productDTO = productService.updateProduct(product, id , request);
            APIResponse<Product> apiResponse = new APIResponse<>(HttpStatus.OK, productDTO);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<?>> deleteProduct(
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            productService.deleteProduct(id , request);
            APIResponse<String> response=new APIResponse<>(HttpStatus.OK,"Product deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }
}