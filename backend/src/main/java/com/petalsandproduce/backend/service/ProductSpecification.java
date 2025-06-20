package com.petalsandproduce.backend.service;

import com.petalsandproduce.backend.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String category) {
        return (root, query, builder) ->
                category == null ? null : builder.equal(root.get("category"), category);
    }

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return builder.or(
                    builder.like(builder.lower(root.get("name")), pattern),
                    builder.like(builder.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Product> hasMinPrice(Double minPrice) {
        return (root, query, builder) ->
                minPrice == null ? null : builder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(Double maxPrice) {
        return (root, query, builder) ->
                maxPrice == null ? null : builder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
