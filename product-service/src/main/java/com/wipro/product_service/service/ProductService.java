package com.wipro.product_service.service;



import com.wipro.product_service.dto.ProductRequest;
import com.wipro.product_service.dto.ProductResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable, HttpServletRequest request);
    ProductResponse getProductById(Long id, HttpServletRequest request);
    ProductResponse createProduct(ProductRequest request, HttpServletRequest servletRequest);
    ProductResponse updateProduct(Long id, ProductRequest request, HttpServletRequest servletRequest);
    void updateProductStock(Long productId, int quantity, HttpServletRequest servletRequest);
    void deleteProduct(Long id, HttpServletRequest servletRequest);
}