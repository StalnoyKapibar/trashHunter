package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.embedded.Coordinates;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/sender")
public class SenderController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @GetMapping("/my_offers")
    public String senderMyOffers() {
        return "sender/sender_my_offers";
    }

    @GetMapping("/new_offer")
    public String getNewOfferPage(Model model, Principal principal){
        String city = userService.findByEmail(principal.getName()).getCity();
        model.addAttribute("city", city);
        return "new_offer";
    }

    @PostMapping("/new_offer")
    public String createNewOffer(@ModelAttribute Offer offer, @ModelAttribute Coordinates coordinates, Principal principal,
                                 @RequestParam(value = "isSorted", required = false) boolean isSorted) {
        User sender = userService.findByEmail(principal.getName());
        offer.setSender((Sender) sender);
        offer.setOfferStatus(OfferStatus.OPEN);
        offer.setSorted(isSorted);
        offer.setCoordinates(coordinates);
        offer.setCreationDateTime(LocalDateTime.now());
        offerService.add(offer);
        return "new_offer";
    }
}
