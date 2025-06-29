package com.petalsandproduce.backend.mapper;

import com.petalsandproduce.backend.DTO.ProductDTO;
import com.petalsandproduce.backend.model.Product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
    }

    public static List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        return product;
    }
}
