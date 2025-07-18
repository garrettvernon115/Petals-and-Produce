package com.petalsandproduce.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.petalsandproduce.backend.DTO.OrderRequestDTO;
import com.petalsandproduce.backend.service.OrderService;
import com.petalsandproduce.backend.DTO.OrderResponseDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequestDTO orderRequest, Principal principal) {
        try {
            orderService.submitOrder(orderRequest, principal);
            return ResponseEntity.ok("Order placed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(Principal principal) {
        try {
            String username = principal.getName();
            List<OrderResponseDTO> orders = orderService.getOrdersForUser(username);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving orders");
        }
    }
}