package com.wipro.product_service.service.impl;

import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ProductNotFoundException;
import com.wipro.product_service.exception.ProductServiceException;
import com.wipro.product_service.model.Product;
import com.wipro.product_service.model.ProductStatus;
import com.wipro.product_service.repository.ProductRepository;
import com.wipro.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PermissionService permissionService;

//    private final ProductRepository productRepository;
//    private final UserServiceClient userServiceClient;
//    private final PermissionService permissionService;

//    @Override
//    public Page<ProductResponse> getAllProducts(Pageable pageable, HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (!permissionService.hasPermission(authHeader, "VIEW_PRODUCT")) {
//            throw new PermissionDeniedException("Insufficient permissions");
//        }
//
//        return productRepository.findAll(pageable)
//                .map(this::mapToProductResponse);
//    }
//
//    @Override
//    public ProductResponse getProductById(Long id, HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (!permissionService.hasPermission(authHeader, "VIEW_PRODUCT")) {
//            throw new PermissionDeniedException("Insufficient permissions");
//        }
//
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
//        return mapToProductResponse(product);
//    }

    @Override
    public Product createProduct(Product product, HttpServletRequest request) throws PermissionDeniedException, ProductServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        if (!permissionService.hasPermission(request, "CREATE_PRODUCT")) {
            throw new PermissionDeniedException();
        }

        product.setId(UUID.randomUUID().toString());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreatedBy(userId);

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ProductServiceException("Failed to create product.");
        }
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status);
    }

//    @Override
//    public ProductResponse updateProduct(Long id, ProductRequest request, HttpServletRequest servletRequest) {
//        String authHeader = servletRequest.getHeader("Authorization");
//        if (!permissionService.hasPermission(authHeader, "EDIT_PRODUCT")) {
//            throw new PermissionDeniedException("Insufficient permissions");
//        }
//
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
//
//        product.setName(request.getName());
//        product.setDescription(request.getDescription());
//        product.setPrice(request.getPrice());
//        product.setStockQuantity(request.getStockQuantity());
//        product.setImageUrl(request.getImageUrl());
//        product.setCategory(request.getCategory());
//
//        // Update status based on stock
//        if (product.getStockQuantity() <= 0) {
//            product.setStatus(ProductStatus.OUT_OF_STOCK);
//        } else if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
//            product.setStatus(ProductStatus.ACTIVE);
//        }
//
//        try {
//            product = productRepository.save(product);
//            return mapToProductResponse(product);
//        } catch (Exception e) {
//            throw new ProductServiceException("Failed to update product: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void updateProductStock(Long productId, int quantity, HttpServletRequest servletRequest) {
//        String authHeader = servletRequest.getHeader("Authorization");
//        if (!permissionService.hasPermission(authHeader, "UPDATE_INVENTORY")) {
//            throw new PermissionDeniedException("Insufficient permissions");
//        }
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//
//        int newQuantity = product.getStockQuantity() + quantity;
//        if (newQuantity < 0) {
//            throw new OutOfStockException("Not enough stock available");
//        }
//
//        product.setStockQuantity(newQuantity);
//
//        // Update status based on new quantity
//        if (newQuantity == 0) {
//            product.setStatus(ProductStatus.OUT_OF_STOCK);
//        } else if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
//            product.setStatus(ProductStatus.ACTIVE);
//        }
//
//        try {
//            productRepository.save(product);
//        } catch (Exception e) {
//            throw new ProductServiceException("Failed to update product stock: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteProduct(Long id, HttpServletRequest servletRequest) {
//        String authHeader = servletRequest.getHeader("Authorization");
//        if (!permissionService.hasPermission(authHeader, "DELETE_PRODUCT")) {
//            throw new PermissionDeniedException("Insufficient permissions");
//        }
//
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
//
//        product.setStatus(ProductStatus.INACTIVE);
//        try {
//            productRepository.save(product);
//        } catch (Exception e) {
//            throw new ProductServiceException("Failed to delete product: " + e.getMessage());
//        }
//    }
//
//    private ProductResponse mapToProductResponse(Product product) {
//        ProductResponse response = new ProductResponse();
//        response.setId(product.getId());
//        response.setName(product.getName());
//        response.setDescription(product.getDescription());
//        response.setPrice(product.getPrice());
//        response.setStockQuantity(product.getStockQuantity());
//        response.setStatus(product.getStatus().name());
//        response.setImageUrl(product.getImageUrl());
//        response.setCategory(product.getCategory());
//        response.setCreatedBy(product.getCreatedBy());
//        if (product.getCreatedAt() != null) {
//            response.setCreatedAt(product.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
//        }
//        if (product.getUpdatedAt() != null) {
//            response.setUpdatedAt(product.getUpdatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
//        }
//        return response;
//    }
}