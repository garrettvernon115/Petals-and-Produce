package com.petalsandproduce.backend.service;
import java.util.List;

import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.request.RegistrationRequest;

public interface UserService {
    void saveUser(RegistrationRequest rr);

    User findByEmail(String email);

    List<RegistrationRequest> findAllUsers();
}
