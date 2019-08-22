package org.bootcamp.trashhunter.controller.rest;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sender")
public class SeekerRestController {

    @Autowired
    OfferService offerService;

    @GetMapping("/my_offers")
    public String senderMyOffers(Model model) {
        List<Offer> offers = offerService.getOffersBySenderId(1L);
        List<Offer> offers1 =  offers.stream().sorted(Comparator.comparing(Offer::isActive)).collect(Collectors.toList());
        model.addAttribute("offers",offerService.getOffersBySenderId(1L));
        return "sender/sender_my_offers";
    }

    @GetMapping("/confirmOffer/{id}")
    public void senderMyOffers(Model model, @PathVariable Long id) {
        offerService.confirmOffer(id);
    }
}
