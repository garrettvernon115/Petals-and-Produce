package com.petalsandproduce.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petalsandproduce.backend.DTO.UserSummaryDTO;
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.repository.UserRepository;
import com.petalsandproduce.backend.request.RegistrationRequest;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationRequest rr) {
        String hashedPassword = passwordEncoder.encode(rr.getPassword());
        User user = new User(rr.getName(), rr.getUsername(), rr.getEmail(), hashedPassword, rr.getRole());
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<RegistrationRequest> findAllUsers() {
        return userRepository.findAll().stream()
            .map(u -> new RegistrationRequest(
                u.getName(), u.getUsername(), u.getEmail(), u.getPassword(), u.getRole()))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public List<UserSummaryDTO> getALLUserSummaries() {
        return userRepository.findAll().stream()
            .map(user -> new UserSummaryDTO(
                user.getId(), 
                user.getName(), 
                user.getEmail(), 
                user.getRole().name()
            ))
            .collect(Collectors.toList());
    }
}
