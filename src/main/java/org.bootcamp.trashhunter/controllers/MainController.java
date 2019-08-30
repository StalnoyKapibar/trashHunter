package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(@RequestParam(value = "city", required = false) String city, Model model,
                       Principal principal, Authentication authentication) {

        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            String userCity = user.getCity();
            model.addAttribute("authId",user.getId());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("auth_map",role);
            model.addAttribute("city", !userCity.isEmpty() ? userCity : "Москва, Россия" );
            return "index";
        }
        if (city == null) {
            return "welcome";
        }
        model.addAttribute("city", city);
        return "index";
    }

    @GetMapping(value = "/index")
    public String mainPage(Authentication authentication,Model model, Principal principal) {
        if (authentication.isAuthenticated()) {
            User user = userService.findByEmail(principal.getName());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("authId",user.getId());
            model.addAttribute("auth_map", role);
            if (role.equals("Sender")) {
                return  "sender/sender_page";
            } else {
                return  "taker/taker_page";
            }
        } else {
            return "map";
        }
    }

    @GetMapping(value = "/update_password")
    public String updatePasswordPage() {
        return "update_password";
    }


}
