package com.petalsandproduce.backend.controller;

import com.petalsandproduce.backend.model.Role; 
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.repository.UserRepository;
import com.petalsandproduce.backend.request.LoginRequest; 
import com.petalsandproduce.backend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
           
            User user = userRepository.findByUsername(loginRequest.getUsername());
            if (user != null) {
                
                if (user.getRole() == Role.ADMIN && !user.getEmail().endsWith("@petalsandproduce.com")) {
                    
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body("Invalid admin email domain");
                }
            }
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
                )
            );

            
            User authenticatedUser = userRepository.findByUsername(loginRequest.getUsername());
            String token = jwtUtil.generateToken(authentication.getName());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", authenticatedUser.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Missing name field");
        }

        User user = userRepository.findByUsername(name);
        if (user == null) {
            return ResponseEntity.ok("No user found to delete"); 
        }

        userRepository.delete(user);
        return ResponseEntity.ok("User deleted");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
