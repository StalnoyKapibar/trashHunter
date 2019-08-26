package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.services.impl.UserService;
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
    public String main(@RequestParam(value = "city", required = false) String city,
                       @RequestParam(value = "error", required = false) boolean error,
                       Model model,
                       Principal principal){
        if (principal != null) {
            String userCity = userService.findByEmail(principal.getName()).getCity();
            model.addAttribute("city", !userCity.isEmpty() ? userCity : "Москва, Россия" );
            return "index";
        }
        if (city == null) {
            return "welcome";
        }
        if (error) {
            model.addAttribute("error", true);
        }
        model.addAttribute("city", city);
        return "index";
    }

    @GetMapping(value = "/index")
    public String mainPage(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
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
    public String updatePasswordPage(Model model, Principal principal) {

        return "update_password";
    }
}
