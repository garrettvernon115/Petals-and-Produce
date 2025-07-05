package com.petalsandproduce.backend.service;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.CartItem;
import com.petalsandproduce.backend.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    Cart findByUserId(long userId) {
        return cartRepository.findByUserId(userId);
    }
    Cart findBySessionId(String sessionId) {
        return cartRepository.findBySessionId(sessionId);
    }
    void deleteByUserID(long userId) {
        cartRepository.deleteByUserId(userId);
    }
    void deleteBySessionId(String sessionId) {
        cartRepository.deleteBySessionId(sessionId);
    }
    void addItemToCart(CartItem cartItem) {
        
    }
    void deleteItemFromCart(CartItem cartItem) {

    }
}
