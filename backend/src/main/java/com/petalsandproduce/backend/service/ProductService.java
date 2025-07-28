package com.petalsandproduce.backend.service;

import com.petalsandproduce.backend.DTO.ProductDTO;
import com.petalsandproduce.backend.exception.ProductNotFoundException;
import com.petalsandproduce.backend.mapper.ProductMapper;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

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

    public ProductDTO addProduct(ProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription()); 
        product.setStock(dto.getStock());             
        product.setImageUrl(dto.getImageUrl()); 

        return ProductMapper.toDTO(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}