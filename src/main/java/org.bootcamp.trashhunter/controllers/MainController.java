package org.bootcamp.trashhunter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/ex")
    public String ex() {
        return "index";
    }

    @GetMapping("/sender_my_offers")
    public String senderMyOffers() { return "sender/sender_my_offers";}
}
