package com.wipro.product_service.repository;

import com.wipro.product_service.model.Category;
import com.wipro.product_service.model.Product;
import com.wipro.product_service.model.ProductStatus;
import com.wipro.product_service.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
    List<Product> findByCategoryAndSubCategory(Category category, SubCategory subCategory);
}