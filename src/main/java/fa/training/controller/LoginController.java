package fa.training.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import fa.training.dto.UserDTO;
import fa.training.model.User;
import fa.training.service.UserService;
import fa.training.utility.Utility;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@PermitAll
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = {"/login", "/", ""})
    public String login(HttpServletRequest request, Authentication authentication) {
        return "pages/login/login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam(value = "password", required = false) String pass,
                                   @RequestParam(value = "username", required = false) String username,
                                   HttpServletRequest request,
                              HttpSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login");


        User userExist =  userService.findByUserName(username);
        if (userExist == null ) {
            modelAndView.addObject("param", "Exception");
            return modelAndView;
        }
        if (!bCryptPasswordEncoder.matches(pass, userExist.getPassword())) {
            modelAndView.addObject("param", "Exception");
            return modelAndView;
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userExist, userDTO);
        userDTO.setPassword("");
        userDTO.setResetPasswordToken("");

        userDTO.setToken(Utility.generateToken(objectMapper.writeValueAsString(userDTO)));
//        session.setAttribute("user", objectMapper.writeValueAsString(userDTO));

        modelAndView.addObject("user", userDTO);
        modelAndView.setViewName("pages/general/blank-page");
        return modelAndView;
    }
}

