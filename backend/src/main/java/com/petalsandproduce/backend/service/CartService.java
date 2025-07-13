package com.petalsandproduce.backend.service;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart getOrCreateCart(User user, String sessionId) {
        Cart cart;
        if (user != null) {
            cart = cartRepository.findByUserId(user.getId());
            if (cart == null) {
                cart = new Cart(user);
                cartRepository.save(cart);
            }
        } else {
            cart = cartRepository.findBySessionId(sessionId);
            if (cart == null) {
                cart = new Cart(sessionId);
                cartRepository.save(cart);
            }
        }
        return cart;
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}