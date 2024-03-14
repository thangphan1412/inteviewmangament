package fa.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "pages/login/login";
    }

    @GetMapping("/home")
    public String home() {
        return "pages/general/blank-page";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

}

