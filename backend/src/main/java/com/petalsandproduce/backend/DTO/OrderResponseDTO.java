package com.petalsandproduce.backend.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderResponseDTO {

    private Long id;
    private BigDecimal totalAmount;
    private String status;
    private Date orderDate;
    private List<OrderItemDTO> items;

    public OrderResponseDTO(Long id, BigDecimal totalAmount, String status, Date orderDate, List<OrderItemDTO> items) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.items = items;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
