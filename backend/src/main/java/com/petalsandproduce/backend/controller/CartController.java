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
 
        // Add item
        CartItem item = new CartItem(cr.getProductId(), cr.getQuantity());
        cart.addToCart(item);
        cartService.saveCart(cart);
 
        return ResponseEntity.ok("Item added to cart successfully.");
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
}