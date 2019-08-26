package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/taker")
public class TakerRestController {

    @Autowired
    OfferService offerService;

    @GetMapping("/my_offers")
    public List<Offer> getAllOffers(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return offerService.getOffersByTaker(email);
    }
}
