package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TakerController {

    @Autowired
    OfferService offerService;

    @GetMapping("/taker/my_offers")
    public String myOffers() {
        return "/taker/taker_my_offers";
    }

    @GetMapping("taker/offer_page/{offerId}")
    public String editOffer(@PathVariable Long offerId, Model model) {
        Offer offer = offerService.getById(offerId);
        model.addAttribute("offer",offer);
        model.addAttribute("longitude",offer.getCoordinates().getLongitude());
        model.addAttribute("latitude",offer.getCoordinates().getLatitude());
        return "/taker/offer_page";
    }

}
