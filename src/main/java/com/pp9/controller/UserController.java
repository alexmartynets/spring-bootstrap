package com.pp9.controller;

import com.pp9.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class.getName());

    @GetMapping("/page")
    public String doGet(Authentication authentication, ModelMap map) {
        log.info("START");
        User userAuth = (User) authentication.getPrincipal();
        map.addAttribute("userAuth", userAuth);
        return "user";
    }
}