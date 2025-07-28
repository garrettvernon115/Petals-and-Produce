package com.petalsandproduce.backend.controller;

import com.petalsandproduce.backend.DTO.OrderRequestDTO;
import com.petalsandproduce.backend.DTO.OrderResponseDTO;
import com.petalsandproduce.backend.DTO.UpdateOrderStatusRequest; 
import com.petalsandproduce.backend.model.Order; 
import com.petalsandproduce.backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            Long orderId = orderService.submitOrder(orderRequest); 
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Order failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            List<OrderResponseDTO> orders = orderService.getOrdersForUser();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Error retrieving orders: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusRequest request) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, request.getStatus());
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}