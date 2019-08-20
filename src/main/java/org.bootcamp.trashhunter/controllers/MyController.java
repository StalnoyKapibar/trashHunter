package org.bootcamp.trashhunter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

    @GetMapping(value = "/updatePassword")
    public ModelAndView updatePasswordPage(WebRequest request, Model model) {
        return new ModelAndView("updatePassword");
    }


}