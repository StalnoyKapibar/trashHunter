package org.bootcamp.trashhunter.controller.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SenderRestController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/api/sender/my_offers")
    public List<Offer> senderMyOffers() {
        return offerService.getOffersBySenderId(1L);
//        if (active!=null) {
//            List<Offer> offers1 =
//                    offers
//                            .stream()
//                            .sorted(Comparator.comparing(Offer::isActive).reversed())
//                            .collect(Collectors.toList());
//
//            model.addAttribute("offers", offers1);
//            model.addAttribute("check","active");
//            return "sender/sender_my_offers";
//        } else{
//            model.addAttribute("offers", offers);
//            return "sender/sender_my_offers";
//        }
    }

    @GetMapping("/confirmOffer/{id}")
    public void confirmMyOffers(@PathVariable Long id) {
        offerService.confirmOffer(id);

    }
}
