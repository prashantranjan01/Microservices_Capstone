package com.wipro.product_service.repository;

import com.wipro.product_service.model.Category;
import com.wipro.product_service.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {
    List<SubCategory> findByCategory(Category category);
}