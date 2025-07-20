package com.petalsandproduce.backend.service;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.CartItem;
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


    // What if want it to explode on failure
    public Cart getCart(User user, String sessionId) {
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
                System.out.println("The cart does not exist");
                return null;
            }
        }
        return cart;
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
 
    public void updateItemQuantity(Cart cart, long productId, int newQuantity) {
    CartItem toUpdate = null;
 
    for (CartItem item : cart.getCartItems()) {
        if (item.getProductId() == productId) {
            toUpdate = item;
            break;
        }
    }
 
    if (toUpdate != null) {
        if (newQuantity == 0) {
            cart.getCartItems().remove(toUpdate);
        } else {
            toUpdate.setQuantity(newQuantity);
        }
        cartRepository.save(cart);
    }
}

    public void removeItemFromCart(Cart cart, long productId) {
        CartItem toRemove = null;

        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId() == productId) {
                toRemove = item;
                break;
            }   
        }

        if (toRemove != null) {
            cart.getCartItems().remove(toRemove);
            cartRepository.save(cart);
        }
    } 
}