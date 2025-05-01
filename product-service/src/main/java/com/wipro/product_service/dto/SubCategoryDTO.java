package com.wipro.product_service.dto;

import com.wipro.product_service.model.Category;
import com.wipro.product_service.model.SubCategory;

import java.time.LocalDateTime;

public class SubCategoryDTO {
    private String id;
    private String title;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SubCategoryDTO(SubCategory subCategory) {
        this.id = subCategory.getId();
        this.title = subCategory.getTitle();
        this.createdAt = subCategory.getCreatedAt();
        this.updatedAt = subCategory.getUpdatedAt();
        this.createdBy = subCategory.getCreatedBy();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
