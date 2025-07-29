package com.petalsandproduce.backend.service;

import com.petalsandproduce.backend.DTO.LowStockAlertDTO;
import com.petalsandproduce.backend.exception.InsufficientStockException;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${app.stock.low-threshold:10}")
    private int lowStockThreshold;

    @Value("${app.stock.critical-threshold:3}")
    private int criticalStockThreshold;

    @Transactional(readOnly = true)
    public boolean isStockAvailable(Long productId, int requestedQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return product.getStock() >= requestedQuantity;
    }

    @Transactional
    public void reserveStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(
                String.format("Insufficient stock for product '%s'. Available: %d, Requested: %d", 
                product.getName(), product.getStock(), quantity)
            );
        }
        
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }


    @Transactional
    public void restoreStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }

    
    @Transactional
    public void updateStock(Long productId, int newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        
        product.setStock(newStock);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public int getCurrentStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return product.getStock();
    }


    @Transactional(readOnly = true)
    public List<LowStockAlertDTO> getLowStockProducts() {
        List<Product> lowStockProducts = productRepository.findByStockLessThanEqual(lowStockThreshold);
        
        return lowStockProducts.stream()
                .map(product -> new LowStockAlertDTO(
                    product.getId(),
                    product.getName(),
                    product.getStock(),
                    product.getStock() <= criticalStockThreshold ? "CRITICAL" : "LOW"
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Product> getOutOfStockProducts() {
        return productRepository.findByStock(0);
    }

    @Transactional(readOnly = true)
    public boolean isOutOfStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return product.getStock() == 0;
    }

    @Transactional(readOnly = true)
    public boolean isLowStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return product.getStock() <= lowStockThreshold && product.getStock() > 0;
    }

    @Transactional(readOnly = true)
    public boolean isCriticalStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return product.getStock() <= criticalStockThreshold && product.getStock() > 0;
    }
}