package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.services.abstraction.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MyController {

    @Autowired
    private UserServiceI userService;

    @GetMapping(value = "/update_password")
    public String updatePasswordPage(Model model, Principal principal) {

        return "update_password";

    }




}