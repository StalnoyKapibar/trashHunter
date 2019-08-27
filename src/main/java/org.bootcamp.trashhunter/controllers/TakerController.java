package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TakerController {

    @Autowired
    OfferService offerService;

    @GetMapping("/taker/my_offers")
    public String myOffers() {
        return "/taker/taker_my_offers";
    }

}
