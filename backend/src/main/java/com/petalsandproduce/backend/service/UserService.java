package com.petalsandproduce.backend.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.request.RegistrationRequest;
import com.petalsandproduce.backend.dto.UserSummaryDTO;

@Service
public interface UserService {
    void saveUser(RegistrationRequest rr);

    User findByEmail(String email);

    List<RegistrationRequest> findAllUsers();

    void deleteByEmail(String email);

    List<UserSummaryDTO> getALLUserSummaries();
}
