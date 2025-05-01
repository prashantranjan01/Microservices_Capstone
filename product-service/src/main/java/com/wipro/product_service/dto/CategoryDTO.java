package com.wipro.product_service.dto;

import com.wipro.product_service.model.Category;

import java.time.LocalDateTime;

public class CategoryDTO {
    private String id;
    private String title;
    private String imageUrl;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.imageUrl = category.getImageUrl();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
        this.createdBy = category.getCreatedBy();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
