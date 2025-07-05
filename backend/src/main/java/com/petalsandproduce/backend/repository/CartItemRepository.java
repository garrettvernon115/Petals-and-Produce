package com.petalsandproduce.backend.repository;

import com.petalsandproduce.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findById(String id);
    CartItem findByProductId(String productId);
    void deleteById(String id);
}
