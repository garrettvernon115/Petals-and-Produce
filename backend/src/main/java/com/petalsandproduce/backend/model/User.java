package com.petalsandproduce.backend.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    protected User() {}

    public User(String name, String email, String password, Role role) {
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.role = role != null ? role : Role.USER;
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

    @Override
    public String toString() {
        return "Username: " + this.name + "; Email: " + this.email + "; Password: " + this.password;
    }
}
