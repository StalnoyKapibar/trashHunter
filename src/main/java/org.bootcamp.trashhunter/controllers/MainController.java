package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.bootcamp.trashhunter.services.impl.SenderService;
import org.bootcamp.trashhunter.services.impl.TakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Autowired
    SenderService senderService;

    @Autowired
    TakerService takerService;

    @Autowired
    OfferService offerService;


    @GetMapping("/ex/{id}")
    public void ex(@PathVariable Long id){
        Sender sender = senderService.getById(1L);
        System.out.println(sender.getName());
    }
}
