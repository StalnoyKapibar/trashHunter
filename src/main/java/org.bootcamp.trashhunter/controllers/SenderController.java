package org.bootcamp.trashhunter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sender")
public class SenderController {

    @GetMapping("/my_offers")
    public String senderMyOffers() {
        return "sender/sender_my_offers";
    }
}
