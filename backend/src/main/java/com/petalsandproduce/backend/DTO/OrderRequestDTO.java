package com.petalsandproduce.backend.DTO;

import java.util.List;

public class OrderRequestDTO {
    private List<CartItemDTO> items;

    // You can add shipping address or notes here later if needed

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
