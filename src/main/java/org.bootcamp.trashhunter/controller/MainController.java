package org.bootcamp.trashhunter.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Authentication authentication) {
//        todo
        if (authentication.isAuthenticated()) {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            if (role.equals("Sender")) {
                ModelAndView mv = new ModelAndView("sender/sender_page");
                return mv;
            } else {
                ModelAndView mv = new ModelAndView("taker/taker_page");
                return mv;
            }
        } else {
            ModelAndView mv = new ModelAndView("map");
            return mv;
        }
    }

}
