package fa.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Xin chào !!!!");
        return "pages/general/blank-page";
    }
}
