package org.bootcamp.trashhunter.controllers.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offer")
public class OfferRestController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/deleteOffer/{offerId}")
    public void deleteOffer(@PathVariable Long offerId) {
        offerService.deleteById(offerId);
    }

    @PostMapping
    public List<Offer> getOffersWithFilterMap(@RequestBody Map<String, Object> map) {
        if (map.size() == 0) {
            return offerService.getAll();
        }
        return offerService.getFilterQuery(map);
    }

    @GetMapping("/confirmOffer/{takerId}/{offerId}")
    public void confirmOffers(@PathVariable Long takerId, @PathVariable Long offerId) {
        offerService.confirmOffer(takerId, offerId);
    }

    @GetMapping("/restoreOffer/{offerId}")
    public void restoreOffer(@PathVariable Long offerId) {
        offerService.restoreOffer(offerId);
    }

    @GetMapping
    public List<Offer> getMeeting() {
        return offerService.getAll();
    }

    @GetMapping("/{id}")
    public Offer getOfferById(@PathVariable("id") long id) {
        return offerService.getById(id);
    }

    @GetMapping("/makeCompleteOffer/{offerId}")
    public void makeCompleteOffer(@PathVariable Long offerId) {
        offerService.makeCompleteOffer(offerId);
    }

    @GetMapping("/deleteOffer/{offerId}")
    public void deleteOffer(@PathVariable Long offerId) {
        offerService.deleteById(offerId);
    }

    @GetMapping("/cancelOffer/{offerId}")
    public void cancelOffer(@PathVariable Long offerId) {
        offerService.cancelOffer(offerId);
    }
}
