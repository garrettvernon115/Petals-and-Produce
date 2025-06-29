package com.petalsandproduce.backend.repository;

import com.petalsandproduce.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(String id);
    Cart findByUserId(String userId);
    void deleteById(String id);
}
