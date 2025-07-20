package com.petalsandproduce.backend.controller;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.Role;
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.request.RegistrationRequest;
import com.petalsandproduce.backend.service.CartService;
import com.petalsandproduce.backend.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@RestController
@RequestMapping("/api")
public class UserController {
 
    private final UserService userService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;
 
    public UserController(UserService userService, CartService cartService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }
 
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest rr) {
        if (rr.getEmail() == null || rr.getEmail().trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("You must input an email address");
        }
 
        if (rr.getName() == null || rr.getName().trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("You must input a username");
        }
 
        if (rr.getPassword() == null || rr.getPassword().trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("You must input a password");
        }
 
        if (userService.findByEmail(rr.getEmail()) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already registered");
        }
 
        // Assign role based on email domain
        if (rr.getEmail().endsWith("@petalsandproduce.com")) {
            rr.setRole(Role.ADMIN);
        } else {
            rr.setRole(Role.USER);
        }
 
        userService.saveUser(rr);
        return ResponseEntity.ok("User registered successfully");
    }
 
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegistrationRequest rr, HttpSession session) {
        User user = userService.findByEmail(rr.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("No user with that email was found");
        }
 
        if (passwordEncoder.matches(rr.getPassword(), user.getPassword())) {
            Cart cart = cartService.getCart(null, session.getId());
            if (cart != null) {
                cartService.saveCart(cart);
            }
            return ResponseEntity.ok("Login successful!");
        }
 
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Username or Password was incorrect.");
    }
 
    @DeleteMapping("/deleteAccount")
    @Transactional
    public ResponseEntity<?> deleteUser(@RequestBody RegistrationRequest rr) {
        User user = userService.findByEmail(rr.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("It's already deleted or something idk how you got here");
        }
 
        if (passwordEncoder.matches(rr.getPassword(), user.getPassword())) {
            userService.deleteByEmail(rr.getEmail());
            return ResponseEntity.ok("Deletion successful!");
        }
 
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Password did not match.");
    }
}
 