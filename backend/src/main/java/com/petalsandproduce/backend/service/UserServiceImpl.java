package com.petalsandproduce.backend.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.petalsandproduce.backend.repository.UserRepository;
import com.petalsandproduce.backend.request.RegistrationRequest;
import com.petalsandproduce.backend.model.User;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(RegistrationRequest rr) {
        User user = new User(rr.getName(), rr.getEmail(), rr.getPassword());
        user.setRole(rr.getRole());
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
                u.getName(), u.getEmail(), u.getPassword(), u.getRole()))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }


}
