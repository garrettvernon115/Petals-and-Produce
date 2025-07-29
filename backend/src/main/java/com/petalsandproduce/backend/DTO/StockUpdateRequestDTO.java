package com.petalsandproduce.backend.DTO;

public class StockUpdateRequestDTO {
    private Long productId;
    private int newStock;

    public StockUpdateRequestDTO() {}

    public StockUpdateRequestDTO(Long productId, int newStock) {
        this.productId = productId;
        this.newStock = newStock;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getNewStock() {
        return newStock;
    }

    public void setNewStock(int newStock) {
        this.newStock = newStock;
    }
}