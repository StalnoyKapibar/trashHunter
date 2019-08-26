package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class testController {

    @GetMapping("/slide")
    public String getUserPage() {

        return "testSlide";
    }
}
