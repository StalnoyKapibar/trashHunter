package org.bootcamp.trashhunter.controller.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sender")
public class SeekerRestController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/my_offers")
    public String senderMyOffers(Model model, @RequestParam(required = false) String active) {
        List<Offer> offers = offerService.getOffersBySenderId(1L);
        if (active!=null) {
            List<Offer> offers1 =
                    offers
                            .stream()
                            .sorted(Comparator.comparing(Offer::isActive).reversed())
                            .collect(Collectors.toList());

            model.addAttribute("offers", offers1);
            model.addAttribute("check","active");
            return "sender/sender_my_offers";
        } else{
            model.addAttribute("offers", offers);
            return "sender/sender_my_offers";
        }
    }

    @GetMapping("/confirmOffer/{id}")
    public ResponseEntity confirmMyOffers(Model model, @PathVariable Long id) {
        offerService.confirmOffer(id);
        return ResponseEntity.ok().build();
    }
}
