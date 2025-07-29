package com.petalsandproduce.backend.DTO;

public class StockValidationResponseDTO {
    private boolean valid;
    private String message;
    private int availableStock;

    public StockValidationResponseDTO() {}

    public StockValidationResponseDTO(boolean valid, String message, int availableStock) {
        this.valid = valid;
        this.message = message;
        this.availableStock = availableStock;
    }

    // Getters and Setters
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}
