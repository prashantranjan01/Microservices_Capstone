package com.wipro.product_service.service;


import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ProductNotFoundException;
import com.wipro.product_service.exception.ProductServiceException;
import com.wipro.product_service.model.Product;
import com.wipro.product_service.model.ProductStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product, HttpServletRequest request) throws PermissionDeniedException, ProductServiceException;
    Product getProductById(String id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    List<Product> getAllProductsByStatus(ProductStatus status);
    
//
//    Product updateProduct(Long id, ProductRequest request, HttpServletRequest servletRequest);
//    Product updateProductStock(Long productId, int quantity, HttpServletRequest servletRequest);
//    String deleteProduct(Long id, HttpServletRequest servletRequest);
}