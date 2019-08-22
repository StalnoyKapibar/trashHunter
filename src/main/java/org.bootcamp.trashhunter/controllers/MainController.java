package org.bootcamp.trashhunter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class MainController {


    @GetMapping(value = "/login")
    public String login(Model model, Principal user) {
        return "login";
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
}
