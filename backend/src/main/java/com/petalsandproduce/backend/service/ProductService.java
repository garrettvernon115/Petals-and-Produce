package com.petalsandproduce.backend.service;

import com.petalsandproduce.backend.exception.ProductNotFoundException;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

        public List<Product> getFilteredProducts(String category, String search, Double minPrice, Double maxPrice) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasKeyword(search))
                .and(ProductSpecification.hasMinPrice(minPrice))
                .and(ProductSpecification.hasMaxPrice(maxPrice));

        return productRepository.findAll(spec);
    }
}