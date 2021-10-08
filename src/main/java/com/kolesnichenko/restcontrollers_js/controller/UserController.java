package com.kolesnichenko.restcontrollers_js.controller;

import com.kolesnichenko.restcontrollers_js.model.User;
import com.kolesnichenko.restcontrollers_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }


    @GetMapping("admin")
    public String listUsers(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("listUsers", userService.findAll());
        model.addAttribute("userEdit", new User());
        return "admin";
    }

    @GetMapping("user")
    public String getUsers(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }
}
