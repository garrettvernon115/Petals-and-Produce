package com.petalsandproduce.backend.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


public class ProductDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be 0 or more")
    private BigDecimal price;
    
    private String imageUrl;

    private String description;

    private int stock;

    private boolean outOfStock;

    private boolean lowStock;

    private boolean criticalStock;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String category, BigDecimal price, String imageUrl, String description, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
        this.outOfStock = stock == 0;
        this.lowStock = stock > 0 && stock <= 10; 
        this.criticalStock = stock > 0 && stock <= 3;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public @NotNull(message = "Price is required") @PositiveOrZero(message = "Price must be 0 or more") BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(@NotNull(message = "Price is required") @PositiveOrZero(message = "Price must be 0 or more") BigDecimal price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
        this.outOfStock = stock == 0;
        this.lowStock = stock > 0 && stock <= 10;
        this.criticalStock = stock > 0 && stock <= 3;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public boolean isLowStock() {
        return lowStock;
    }

    public void setLowStock(boolean lowStock) {
        this.lowStock = lowStock;
    }

    public boolean isCriticalStock() {
        return criticalStock;
    }

    public void setCriticalStock(boolean criticalStock) {
        this.criticalStock = criticalStock;
    }
}

