package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/taker")
public class TakerRestController {

    @Autowired
    OfferService offerService;

    @PostMapping("/my_offers")
    public List<Offer> getOffersWithFilterMap(@RequestBody(required = false) Map<String, Object> map) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (map.size() != 2) {
            return offerService.getFilterOffersForTaker(map,email);
        }
        return offerService.getOffersByTaker(email);
    }
}
