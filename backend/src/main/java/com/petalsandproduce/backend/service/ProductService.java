package com.petalsandproduce.backend.service;

import com.petalsandproduce.backend.exception.ProductNotFoundException;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; 

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    
    /**
     * Retrieves all products from the database.
     * @return a list of all products.
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}