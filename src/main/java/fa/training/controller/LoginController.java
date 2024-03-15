package fa.training.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController implements ErrorController {

    @GetMapping("/login")
    public String login() {
        return "pages/login/login";
    }

    @GetMapping("/home")
    public String home() {
        return "pages/general/blank-page";
    }

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        return "redirect:/home";
    }

    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

}

