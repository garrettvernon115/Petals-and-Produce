package com.petalsandproduce.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.petalsandproduce.backend.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    void deleteByEmail(String email);
}
