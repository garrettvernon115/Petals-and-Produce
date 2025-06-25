package com.petalsandproduce.backend.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.request.RegistrationRequest;

@Service
public interface UserService {
    void saveUser(RegistrationRequest rr);

    User findByEmail(String email);

    List<RegistrationRequest> findAllUsers();

    void deleteByEmail(String email);
}
