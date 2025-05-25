package com.wipro.product_service.service;


import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ProductNotFoundException;
import com.wipro.product_service.exception.ResourceServiceException;
import com.wipro.product_service.model.Product;
import com.wipro.product_service.model.ProductStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product, String categoryId , String subCategoryId , HttpServletRequest request) throws PermissionDeniedException, ResourceServiceException;
    Product getProductById(String id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    List<Product> getAllProductsByStatus(ProductStatus status);
    List<Product> getProductsByCategoryIdAndSubCategoryId(String categoryId , String subCategoryId);
    List<Product> getProductsByQuery(String q);

//
//    Product updateProduct(Long id, ProductRequest request, HttpServletRequest servletRequest);
//    Product updateProductStock(Long productId, int quantity, HttpServletRequest servletRequest);
//    String deleteProduct(Long id, HttpServletRequest servletRequest);
}