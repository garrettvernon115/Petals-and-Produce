package com.petalsandproduce.backend.controller;

import com.petalsandproduce.backend.DTO.LowStockAlertDTO;
import com.petalsandproduce.backend.DTO.StockUpdateRequestDTO;
import com.petalsandproduce.backend.DTO.StockValidationResponseDTO;
import com.petalsandproduce.backend.exception.InsufficientStockException;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/validate/{productId}/{quantity}")
    public ResponseEntity<StockValidationResponseDTO> validateStock(
            @PathVariable Long productId, 
            @PathVariable int quantity) {
        
        try {
            boolean isAvailable = stockService.isStockAvailable(productId, quantity);
            int currentStock = stockService.getCurrentStock(productId);
            
            if (isAvailable) {
                return ResponseEntity.ok(new StockValidationResponseDTO(
                    true, 
                    "Stock available", 
                    currentStock
                ));
            } else {
                return ResponseEntity.ok(new StockValidationResponseDTO(
                    false, 
                    String.format("Insufficient stock. Available: %d, Requested: %d", currentStock, quantity), 
                    currentStock
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StockValidationResponseDTO(
                false, 
                "Product not found", 
                0
            ));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Integer> getCurrentStock(@PathVariable Long productId) {
        try {
            int stock = stockService.getCurrentStock(productId);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/alerts/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LowStockAlertDTO>> getLowStockAlerts() {
        List<LowStockAlertDTO> alerts = stockService.getLowStockProducts();
        return ResponseEntity.ok(alerts);
    }


    @GetMapping("/out-of-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> outOfStockProducts = stockService.getOutOfStockProducts();
        return ResponseEntity.ok(outOfStockProducts);
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateStock(@RequestBody StockUpdateRequestDTO request) {
        try {
            stockService.updateStock(request.getProductId(), request.getNewStock());
            return ResponseEntity.ok("Stock updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check/out-of-stock/{productId}")
    public ResponseEntity<Boolean> isOutOfStock(@PathVariable Long productId) {
        try {
            boolean outOfStock = stockService.isOutOfStock(productId);
            return ResponseEntity.ok(outOfStock);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check/low-stock/{productId}")
    public ResponseEntity<Boolean> isLowStock(@PathVariable Long productId) {
        try {
            boolean lowStock = stockService.isLowStock(productId);
            return ResponseEntity.ok(lowStock);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}