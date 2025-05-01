package com.wipro.product_service.controller;

import com.wipro.product_service.dto.APIResponse;
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
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<APIResponse<List<Product>>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        APIResponse<List<Product>> listAPIResponse = new APIResponse<>(HttpStatus.OK , productList);
        return ResponseEntity.ok(listAPIResponse);
    }

    @GetMapping("/product/{id}")
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

    @GetMapping("/product")
    public ResponseEntity<APIResponse<List<Product>>> getProductByStatus(@RequestParam ProductStatus status) {
        List<Product> productList = productService.getAllProductsByStatus(status);
        APIResponse<List<Product>> listAPIResponse = new APIResponse<>(HttpStatus.OK , productList);
        return ResponseEntity.ok(listAPIResponse);
    }

    @PostMapping("/category/{categoryId}/sub-category/{subCategoryId}/product")
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

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductResponse> updateProduct(
//            @PathVariable Long id,
//            @RequestBody ProductRequest request,
//            HttpServletRequest servletRequest) {
//        return ResponseEntity.ok(productService.updateProduct(id, request, servletRequest));
//    }
//
//    @PatchMapping("/{id}/stock")
//    public ResponseEntity<Void> updateStock(
//            @PathVariable Long id,
//            @RequestParam int quantity,
//            HttpServletRequest servletRequest) {
//        productService.updateProductStock(id, quantity, servletRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(
//            @PathVariable Long id,
//            HttpServletRequest servletRequest) {
//        productService.deleteProduct(id, servletRequest);
//        return ResponseEntity.noContent().build();
//    }
}