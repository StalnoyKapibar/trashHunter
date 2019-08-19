package org.bootcamp.trashhunter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/ex")
    public String ex() {
        return "exp";
    }
}
