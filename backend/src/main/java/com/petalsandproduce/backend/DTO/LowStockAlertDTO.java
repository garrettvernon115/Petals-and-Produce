
package com.petalsandproduce.backend.DTO;

public class LowStockAlertDTO {
    private Long productId;
    private String productName;
    private int currentStock;
    private String alertLevel; // "LOW" or "CRITICAL"

    public LowStockAlertDTO() {}

    public LowStockAlertDTO(Long productId, String productName, int currentStock, String alertLevel) {
        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
        this.alertLevel = alertLevel;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public String getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }
}
