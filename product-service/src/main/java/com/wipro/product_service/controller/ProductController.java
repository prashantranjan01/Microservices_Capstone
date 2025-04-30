package com.wipro.product_service.controller;

import com.wipro.product_service.dto.ProductRequest;
import com.wipro.product_service.dto.ProductResponse;
import com.wipro.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            Pageable pageable,
            HttpServletRequest request) {
        return ResponseEntity.ok(productService.getAllProducts(pageable, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id,
            HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductById(id, request));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(request, servletRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, request, servletRequest));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity,
            HttpServletRequest servletRequest) {
        productService.updateProductStock(id, quantity, servletRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            HttpServletRequest servletRequest) {
        productService.deleteProduct(id, servletRequest);
        return ResponseEntity.noContent().build();
    }
}