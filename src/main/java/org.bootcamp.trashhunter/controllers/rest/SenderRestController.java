package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sender")
public class SenderRestController {

    @Autowired
    private OfferService offerService;

    @GetMapping(value = "/my_offers",  produces = "application/json")
    public Map<Offer, List<Taker>> senderMyOffers() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return offerService.getOffersBySenderIdActiveFirst(name);
    }

}
