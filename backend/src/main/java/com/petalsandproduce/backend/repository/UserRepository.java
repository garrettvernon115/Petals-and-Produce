package com.petalsandproduce.backend.repository;

import com.petalsandproduce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void deleteByEmail(String email);
    User findByEmail(String email);

}
