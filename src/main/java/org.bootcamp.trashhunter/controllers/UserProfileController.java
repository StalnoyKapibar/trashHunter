package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String getUserPage(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("isHolder", true);
        return "user_profile";
    }

    @GetMapping("/profile/{id}")
    public String getUserPage(@PathVariable("id") Long id, Model model, Principal principal) {

         User userFromSession = userService.findByEmail(principal.getName());
        if (id == userFromSession.getId()) {
            model.addAttribute("isHolder", true);
            model.addAttribute("user", userFromSession);
        } else {
            User userFromPath = userService.findById(id);
            model.addAttribute("user", userFromPath);
            model.addAttribute("principalId",userFromSession.getId());
        }
        return "user_profile";
    }
}
