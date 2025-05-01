package com.wipro.product_service.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category {
    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    private String title;

    private String imageUrl;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<SubCategory> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

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

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
