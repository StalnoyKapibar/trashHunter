package org.bootcamp.trashhunter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

    @GetMapping(value = "/updatePassword")
    public ModelAndView updatePassword(WebRequest request, Model model) {
        return new ModelAndView ("UpdatePassword");
    }
}
