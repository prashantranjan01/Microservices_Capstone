package com.wipro.product_service.dto;

import com.wipro.product_service.model.Category;

public class CategoryDTO {
    private String id;
    private String title;
    private String imageUrl;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.imageUrl = category.getImageUrl();
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
