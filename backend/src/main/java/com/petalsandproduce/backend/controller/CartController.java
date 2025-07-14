package com.petalsandproduce.backend.controller;

import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.request.UpdateCartRequest;
import com.petalsandproduce.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("/update")
    public ResponseEntity<Cart> updateItemQuantity(Authentication authentication, @RequestBody UpdateCartRequest request) {
        String username = authentication.getName();
        Cart updatedCart = cartService.updateItemQuantity(
            username, 
            request.getProductId(), 
            request.getNewQuantity()
        );
        return ResponseEntity.ok(updatedCart);
    }
}
