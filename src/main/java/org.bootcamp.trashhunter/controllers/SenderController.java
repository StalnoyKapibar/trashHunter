package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sender")
public class SenderController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/my_offers")
    public String senderMyOffers() {
        return "sender/sender_my_offers";
    }

    @GetMapping("/new_offer")
    public String getNewOfferPage(){
        return "new_offer";
    }
}
