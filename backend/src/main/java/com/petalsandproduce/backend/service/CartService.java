package com.petalsandproduce.backend.service;

import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.CartItem;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.repository.CartRepository;
import com.petalsandproduce.backend.repository.ProductRepository;
import com.petalsandproduce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Cart updateItemQuantity(String username, Long productId, int newQuantity) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        Optional<CartItem> existingItemOptional = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (newQuantity > 0) {
            if (existingItemOptional.isPresent()) {
                existingItemOptional.get().setQuantity(newQuantity);
            } else {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setQuantity(newQuantity);
                newItem.setCart(cart);
                cart.getItems().add(newItem);
            }
        } else {
            existingItemOptional.ifPresent(item -> cart.getItems().remove(item));
        }

        recalculateCartTotal(cart);
        return cartRepository.save(cart);
    }
    
    private void recalculateCartTotal(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            // Assuming product.getPrice() returns a BigDecimal, which is correct for currency.
            BigDecimal itemPrice = item.getProduct().getPrice();
            BigDecimal itemTotal = itemPrice.multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }
        cart.setTotalAmount(total);
    }

    public Cart getOrCreateCart(User user, String sessionId) {
        if (user != null) {
            return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
                Cart newCart = new Cart(user);
                return cartRepository.save(newCart);
            });
        } else {
            return cartRepository.findBySessionId(sessionId).orElseGet(() -> {
                Cart newCart = new Cart(sessionId);
                return cartRepository.save(newCart);
            });
        }
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
