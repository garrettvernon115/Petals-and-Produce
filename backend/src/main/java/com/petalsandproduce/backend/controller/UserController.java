package com.petalsandproduce.backend.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petalsandproduce.backend.model.Role;
import com.petalsandproduce.backend.request.RegistrationRequest;
import com.petalsandproduce.backend.service.UserService;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest rr) {
        // Check if email already exists
        if (userService.findByEmail(rr.getEmail()) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already registered");
        }

        // Auto-assign ADMIN if email ends with our domain
        if (rr.getEmail().endsWith("@petalsandproduce.com")) {
            rr.setRole(Role.ADMIN);
        } else {
            rr.setRole(Role.USER);
        }

        userService.saveUser(rr);
        return ResponseEntity.ok("User registered successfully");
    }
}
