package org.bootcamp.trashhunter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/taker")
public class TakerController {

    @GetMapping("/my_offers")
    public String myOffers(){
        return "/taker/taker_my_offers";
    }
}
