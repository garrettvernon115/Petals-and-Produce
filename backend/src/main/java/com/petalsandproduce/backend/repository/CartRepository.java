package com.petalsandproduce.backend.repository;

import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    Optional<Cart> findByUser(User user);
    
    Optional<Cart> findByUserId(Long userId);

    Optional<Cart> findBySessionId(String sessionId);
}
