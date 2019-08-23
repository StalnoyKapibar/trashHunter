package org.bootcamp.trashhunter.controller;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(@RequestParam(value = "city", required = false) String city, Model model, Principal principal){
        if (principal != null) {
            String userCity = userService.findByEmail(principal.getName()).getCity();
            model.addAttribute("city", !userCity.isEmpty() ? userCity : "Москва, Россия" );
            return "index";
        }
        if (city == null) {
            return "welcome";
        }
        model.addAttribute("city", city);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Model model, String error, String logout, Principal user) {

        ModelAndView mv = new ModelAndView("/index");
        if (user != null) {
            return mv;
        }
        return new ModelAndView("/login");
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Authentication authentication) {

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
