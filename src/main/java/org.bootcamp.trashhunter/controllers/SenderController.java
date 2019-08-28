package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.embedded.Coordinates;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
    public String getNewOfferPage(Model model, Principal principal) {
        model.addAttribute("city", userService.findByEmail(principal.getName()).getCity());
        return "/sender/new_offer";
    }

    @GetMapping("/edit_offer/{offerId}")
    public String editOffer(@PathVariable Long offerId, Model model) {
        Offer offer = offerService.getById(offerId);
        model.addAttribute("offer",offer);
        model.addAttribute("longitude",offer.getCoordinates().getLongitude());
        model.addAttribute("latitude",offer.getCoordinates().getLatitude());
        return "/sender/edit_offer";
    }

    @PostMapping("/edit_offer")
    public String changeOffer(@ModelAttribute Offer offer, @ModelAttribute Coordinates coordinates, Model model,
                              @RequestParam(value = "isSorted", required = false) boolean isSorted) {
        Offer offer1 = offerService.getById(offer.getId());
        offer.setSender(offer1.getSender());
        offer.setIsSorted(isSorted);
        offer.setCoordinates(coordinates);
        offer.setCreationDateTime(offer1.getCreationDateTime());
        offer.setOfferStatus(offer1.getOfferStatus());
        offer.setRespondingTakers(offer1.getRespondingTakers());
        offerService.update(offer);
        model.addAttribute("hasCompleted", true);
        model.addAttribute("offer",offer);
        model.addAttribute("longitude",offer.getCoordinates().getLongitude());
        model.addAttribute("latitude",offer.getCoordinates().getLatitude());
        return "/sender/edit_offer";
    }

    @PostMapping("/new_offer")
    public String createNewOffer(@ModelAttribute Offer offer, @ModelAttribute Coordinates coordinates, Principal principal,
                                 @RequestParam(value = "isSorted", required = false) boolean isSorted, Model model) {
        User sender = userService.findByEmail(principal.getName());
        offer.setSender((Sender) sender);
        offer.setOfferStatus(OfferStatus.OPEN);
        offer.setIsSorted(isSorted);
        offer.setCoordinates(coordinates);
        offer.setCreationDateTime(LocalDateTime.now());
        offerService.add(offer);
        model.addAttribute("hasCompleted", true);
        model.addAttribute("city",userService.findByEmail(principal.getName()).getCity());
        return "/sender/new_offer";
    }

}
