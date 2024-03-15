package fa.training.controller;

import fa.training.model.User;
import fa.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "/pages/register/register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        {
            String userName = user.getUserName();

            if (userService.getNumberUserNameExist(userName.toString()) != null) return ResponseEntity.badRequest().body("Username existed.");
            user.setActive(true);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String rawPassword = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(rawPassword));

            userService.createUser(user);

        }
        return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
    }
}
