package com.petalsandproduce.backend.controller;

import java.net.Authenticator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petalsandproduce.backend.exception.ProductNotFoundException;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.CartItem;
import com.petalsandproduce.backend.model.Product;
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
        // We've confirmed both of these are real values so we can just call this raw
        CartItem item = new CartItem(cr.getProductId(),cr.getQuantity());

        // What we didn't do is make sure that the cart actually exists before trying to call methods from it
        if (!cart.isPresent()) {
            // Create a new cart
        }
        cart.get().addToCart(item);
        return ResponseEntity.ok("Product has been added! Probably.");
    }
}
