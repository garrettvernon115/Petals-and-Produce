import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class UserController {

    
    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        RegistrationRequest rr = new RegistrationRequest();
        model.addAttribute("user", rr);
        return "user";
    }
}
