package com.petalsandproduce.backend.DTO;

import java.math.BigDecimal;

public class CartItemResponse {
    private Long productId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;

    public CartItemResponse(Long productId, String name, BigDecimal price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
