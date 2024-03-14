package fa.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register() {
        return "/pages/register/register";
    }

    @PostMapping("/register")
    public String register(Object object) {
        return "/pages/register/register";
    }
}
