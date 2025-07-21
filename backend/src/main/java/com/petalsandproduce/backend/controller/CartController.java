package com.petalsandproduce.backend.controller;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petalsandproduce.backend.DTO.CartItemResponse;
import com.petalsandproduce.backend.exception.ProductNotFoundException;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.CartItem;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.repository.CartRepository;
import com.petalsandproduce.backend.request.AddToCartRequest;
import com.petalsandproduce.backend.request.UpdateCartRequest;
import com.petalsandproduce.backend.service.CartService;
import com.petalsandproduce.backend.service.ProductService;
 
import jakarta.servlet.http.HttpSession;
 
//@EnableJpaRepositories("com.petalsandproduce.backend.*")
 //@ComponentScan(basePackages = { "com.petalsandproduce.backend.*" })
 @EntityScan("com.petalsandproduce.backend.*")
 
@RestController
@RequestMapping("/api")
public class CartController {
 
    @Autowired
    private CartRepository cartRepository;
 
    @Autowired
    private CartService cartService;
 
    @Autowired
    private ProductService productService;
 
    @PostMapping("/addToCart")
    public ResponseEntity<?> addItemToCart(@RequestBody AddToCartRequest cr, HttpSession session) {
        // Get authenticated user or null
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            currentUser = (User) auth.getPrincipal();
        }
 
        // Validate product
        try {
            productService.findProductById(cr.getProductId());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This item doesn't exist");
        }
 
        if (cr.getQuantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be positive");
        }
 
        // Create or retrieve cart
        Cart cart = cartService.getOrCreateCart(currentUser, session.getId());
 
        // Add item (merge if exists)
       boolean itemExists = false;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId() == cr.getProductId()) {
                item.setQuantity(item.getQuantity() + cr.getQuantity());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            CartItem newItem = new CartItem(cr.getProductId(), cr.getQuantity());
            newItem.setCart(cart);
            cart.addToCart(newItem);
        }

        cartService.saveCart(cart);
 
        return ResponseEntity.ok("Item added to cart successfully.");
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCart(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            currentUser = (User) auth.getPrincipal();
        }
        Cart cart = cartService.getOrCreateCart(currentUser, session.getId());


        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        List<CartItemResponse> response = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            Product product = productService.findProductById(item.getProductId());
            if (product != null) {
                response.add(new CartItemResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    item.getQuantity()
                ));
            }
        }

        return ResponseEntity.ok(response);
        }
 
    @PostMapping("/cart/update")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartRequest request, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
 
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            currentUser = (User) auth.getPrincipal();
        }
 
        Cart cart = cartService.getOrCreateCart(currentUser, session.getId());
 
        // Update or remove item
        cartService.updateItemQuantity(cart, request.getProductId(), request.getNewQuantity());
 
        return ResponseEntity.ok("Cart updated successfully.");
    }

    @DeleteMapping("/cart/remove/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable long productId, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            currentUser = (User) auth.getPrincipal();
        }

        Cart cart = cartService.getOrCreateCart(currentUser, session.getId());

        boolean found = false;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId() == productId) {
                found = true;
                break;
            }
        }

        if (!found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item not found in cart.");
        }

        cartService.removeItemFromCart(cart, productId);
        return ResponseEntity.ok("Item successfully removed from cart.");
    }
}