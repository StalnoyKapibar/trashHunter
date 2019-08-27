package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;
    @GetMapping("/user_profile")
    public String getUserPage(Principal principal, Model model) {

        String principalName = principal.getName();
        User user = userService.findByEmail(principalName);
        model.addAttribute("user", user);

        return "user_profile";
    }
}
