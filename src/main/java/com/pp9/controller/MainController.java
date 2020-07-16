package com.pp9.controller;

import com.pp9.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class MainController {
    private static Logger log = Logger.getLogger(MainController.class.getName());

    @GetMapping
    public String doGet(Authentication authentication, ModelMap map) {
        log.info("doGet START");
        User user = (User) authentication.getPrincipal();
        map.put("user", user);
        log.info("ROLE SET: " + user.getRoleString());
        if (user.getRoleString().contains("ADMIN")) {
            return "redirect:/admin/page";
        } else {
            return "redirect:/user/page";
        }
    }
}
