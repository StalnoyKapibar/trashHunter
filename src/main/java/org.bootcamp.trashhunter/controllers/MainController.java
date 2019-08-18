package org.bootcamp.trashhunter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Model model, String error, String logout, Principal user) {
        ModelAndView mv = new ModelAndView("/index");
        if(user != null) {
            return mv;
        }
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Model model, String error, String logout, Principal user, Authentication auth) {
        ModelAndView mv = new ModelAndView("/index");
        return mv;
    }

}
