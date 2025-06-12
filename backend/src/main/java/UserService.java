import java.util.List;

public interface UserService {
    void saveUser(RegistrationRequest rr);

    User findByEmail(String email);

    List<RegistrationRequest> findAllUsers();
}
