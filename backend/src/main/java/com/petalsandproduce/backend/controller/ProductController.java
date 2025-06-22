package com.petalsandproduce.backend.controller;

import com.petalsandproduce.backend.DTO.ProductDTO;
import com.petalsandproduce.backend.mapper.ProductMapper;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getFilteredProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Product> products = productService.getFilteredProducts(category, search, minPrice, maxPrice);
        List<ProductDTO> productDTOs = ProductMapper.toDTOList(products);

        return ResponseEntity.ok(productDTOs);
    }
}