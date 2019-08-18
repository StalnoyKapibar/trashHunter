package org.bootcamp.trashhunter.config;

import org.bootcamp.trashhunter.services.impl.OfferService;
import org.bootcamp.trashhunter.services.impl.SenderService;
import org.bootcamp.trashhunter.services.impl.TakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitData {

    @Autowired
    OfferService offerService;

    @Autowired
    SenderService senderService;

    @Autowired
    TakerService takerService;

    private void init() {
        initOffers();
        initSenders();
        initTakers();
    }

    private void initOffers(){

    }

    private void initSenders(){

    }

    private void initTakers(){

    }

}
