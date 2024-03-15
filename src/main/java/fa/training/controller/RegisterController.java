package fa.training.controller;

import fa.training.model.User;
import fa.training.service.UserService;
import fa.training.spring_security.PasswordGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;

@Controller
@PermitAll
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "/pages/register/register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception {
        {
            // xử lý thông tin candidate được gửi lên từ form

            String userName = user.getUserName();

            Integer numberUserNameExist = userService.getNumberUserNameExist(userName.toString());
            if (numberUserNameExist > 0) {
                throw new Exception();
            }
            user.setActive(true);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String rawPassword = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(rawPassword));

            userService.createUser(user);

        }
        return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
    }
}
