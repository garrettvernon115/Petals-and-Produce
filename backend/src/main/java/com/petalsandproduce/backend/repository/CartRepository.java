package com.petalsandproduce.backend.repository;

import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(User user);
    void deleteById(int id);
    Cart findByUserId(long userId);
    void deleteByUserId(long userId);
    Cart findBySessionId(String sessionId);
    void deleteBySessionId(String sessionId);
    
}
