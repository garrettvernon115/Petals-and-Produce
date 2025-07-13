package com.petalsandproduce.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petalsandproduce.backend.exception.ProductNotFoundException;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.CartItem;
import com.petalsandproduce.backend.repository.CartRepository;
import com.petalsandproduce.backend.request.AddToCartRequest;
import com.petalsandproduce.backend.service.ProductService;

//@EnableJpaRepositories("com.petalsandproduce.backend.*")
 //@ComponentScan(basePackages = { "com.petalsandproduce.backend.*" })
 @EntityScan("com.petalsandproduce.backend.*")

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addItemToCart(@RequestBody AddToCartRequest cr) {
        // Testing user authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        System.out.println(currentUsername);
        // Retrieve the cart 
        Optional<Cart> cart = cartRepository.findById(cr.getId());
        // Check for bad requests
        try {
            productService.findProductById(cr.getProductId());
        } catch (ProductNotFoundException e) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("This item doesn't exist");
        }
        if (cr.getQuantity() < 0) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Quantity can not be a negative number");
        }
        
        CartItem item = new CartItem(cr.getProductId(),cr.getQuantity());

        if (!cart.isPresent()) {
            // Create a new cart
        }
        cart.get().addToCart(item);
        return ResponseEntity.ok("Product has been added! Probably.");
    }
}
