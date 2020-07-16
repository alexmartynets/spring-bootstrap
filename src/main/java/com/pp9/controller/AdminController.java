package com.pp9.controller;

import com.pp9.model.Role;
import com.pp9.model.User;
import com.pp9.service.UserService;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    private static Logger log = Logger.getLogger(AdminController.class.getName());

    @GetMapping("/page")
    public String getPage(Authentication authentication, ModelMap modelMap) {
        log.info("START");
        List<User> users = userService.findAll();
        Set<String> roleSet = userService.findAllRole();
        log.info("Set roles: " + roleSet);
        User userAuth = (User) authentication.getPrincipal();
        modelMap
                .addAttribute("userAuth", userAuth)
                .addAttribute("lists", users)
                .addAttribute("roleSet", roleSet);
        return "admin";
    }


    @PostMapping("/delete")
    public String postDelete(@RequestParam(value = "id") Long id) {
        log.info("START DELETE");
        userService.delete(userService.findByID(id).get());
        return "redirect:/admin/page";
    }

    @PostMapping("/update")
    public String postUpdate(User user, @RequestParam(value = "roles") String[] roles) throws NotFoundException {
        log.info("START UPDATE_END");
        log.info("user for update: " + user.toString());
        userService.update(user, roles);
        return "redirect:/admin/page";
    }

    @PostMapping("/registration")
    public String postRegistration(User user, ModelMap modelMap, @RequestParam(value = "roles") String[] roles) {
        log.info("SAVE USER START");
        String result = "redirect:/admin/registration";
        System.out.println(user.toString());
        Arrays.stream(roles).forEach(System.out::println);
        if (userService.registration(user, roles)) {
            log.info("USER SAVED AND REDIRECTED FOR ADMIN PAGE");
            result = "redirect:/admin/page";
        } else {
            log.info("LOGIN IS EXIST");
            modelMap.put("message", "User with this name is already exist. Choose new name.");
        }
        return result;
    }


}
