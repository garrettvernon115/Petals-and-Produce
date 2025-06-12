package com.petalsandproduce.backend.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    protected User() {}

    public User(String name, String email, String password) {
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        // Probably want encryption logic
        this.password = Objects.requireNonNull(password);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email);
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password);
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public String toString() {
        String s = "Username: " + this.name + "; Email: " + this.email + "; Password: " + this.password;
        return s;
    }

}
