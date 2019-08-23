package org.bootcamp.trashhunter.controller.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.TrashType;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offer")
public class OfferRestController {

    @Autowired
    private OfferService offerService;

    @PostMapping
    public List<Offer> getOffers(@RequestBody Map<String, Object> map) {
        if (map.size() == 0) {
    @GetMapping("/deleteOffer/{offerId}")
    public void deleteOffer(@PathVariable Long offerId){
        offerService.deleteById(offerId);
    }

    @PostMapping("/getMeeting")
    public List<Offer> getMeeting(@RequestBody Map<String, Object> map) {
        if (map.size() == 0) {
            return offerService.getAll();
        }
        return offerService.getFilterQuery(map);
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAll();
    }
}
