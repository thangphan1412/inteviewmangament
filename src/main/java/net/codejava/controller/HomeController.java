/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.controller;

import net.codejava.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author thangphan
 */
@Controller
public class HomeController {
    @RequestMapping(value = {"/", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(@ModelAttribute LoginDTO loginDTO) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
//        example authen
        if ("test".equals(loginDTO.getUsername()) && "test".equals(loginDTO.getPassword())) {
            modelAndView.setViewName("candidateview");
        }
        return modelAndView;
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
