package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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

    @GetMapping("/confirmOffer/{takerId}/{offerId}")
    public void confirmOffers(@PathVariable Long takerId, @PathVariable Long offerId) { offerService.confirmOffer(takerId,offerId); }

    @GetMapping("/makeCompleteOffer/{offerId}")
    public void makeCompleteOffer(@PathVariable Long offerId) { offerService.makeCompleteOffer(offerId); }

    @GetMapping("/restoreOffer/{offerId}")
    public void restoreOffer(@PathVariable Long offerId) {
        offerService.restoreOffer(offerId);
    }

    @GetMapping("/cancelOffer/{offerId}")
    public void cancelOffer(@PathVariable Long offerId) {
        offerService.cancelOffer(offerId);
    }

    @GetMapping("/deleteOffer/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteById(id);
    }
}
