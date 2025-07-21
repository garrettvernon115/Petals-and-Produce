package com.petalsandproduce.backend.DTO;

import java.math.BigDecimal;

public class OrderItemDTO {

    private String name;
    private int quantity;
    private BigDecimal unitPrice;
    
        public OrderItemDTO(String name, BigDecimal unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}