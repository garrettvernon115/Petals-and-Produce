package com.petalsandproduce.backend.DTO;

import com.petalsandproduce.backend.model.Role;

public class UserDTO {
    private String name;
    private String email;
    private Role role;

    public UserDTO(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}
