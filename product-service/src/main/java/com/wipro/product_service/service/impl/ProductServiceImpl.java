package com.wipro.product_service.service.impl;

import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.exception.ProductNotFoundException;
import com.wipro.product_service.exception.ResourceNotFoundException;
import com.wipro.product_service.exception.ResourceServiceException;
import com.wipro.product_service.model.*;
import com.wipro.product_service.repository.CategoryRepository;
import com.wipro.product_service.repository.ProductRepository;
import com.wipro.product_service.repository.SubCategoryRepository;
import com.wipro.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    PermissionService permissionService;

    @Override
    @Transactional
    public Product createProduct(Product product,String categoryId , String subCategoryId , HttpServletRequest request) throws PermissionDeniedException, ResourceServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        if (!permissionService.hasPermission(request, String.valueOf(ProductAction.CREATE_PRODUCT))) {
            throw new PermissionDeniedException();
        }

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));;
        product.setCategory(category);

        SubCategory subCategory = this.subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id :" + subCategoryId));;
        product.setSubCategory(subCategory);

        product.setId(UUID.randomUUID().toString());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreatedBy(userId);

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ResourceServiceException("Failed to create product.");
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

    @Override
    public List<Product> getProductsByCategoryIdAndSubCategoryId(String categoryId, String subCategoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));;
        SubCategory subCategory = this.subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id :" + subCategoryId));;
        return this.productRepository.findByCategoryAndSubCategory(category , subCategory);
    }

    @Override
    public List<Product> getProductsByQuery(String q) {
        return this.productRepository.findByNameContainingOrDescriptionContaining(q,q);
    }

    @Override
    public Product updateProduct(Product product, String id, HttpServletRequest servletRequest) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ResourceServiceException("Category with id: "+id+" not found");
        }
        Product existingProduct = optionalProduct.get();
        if (product.getImageUrl() != null) {
            existingProduct.setImageUrl(product.getImageUrl());
        }
        if(product.getName()!=null){
            existingProduct.setName(product.getName());
        }
        if(product.getDescription()!=null){
            existingProduct.setDescription(product.getDescription());
        }
        if(product.getStatus()!=null){
            existingProduct.setStatus(product.getStatus());
        }
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());

        existingProduct.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(String id, HttpServletRequest servletRequest) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ResourceServiceException("Product with id: "+id+" not found");
        }
        this.productRepository.delete(optionalProduct.get());
    }
}