package org.bootcamp.trashhunter.config;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.bootcamp.trashhunter.services.impl.SenderService;
import org.bootcamp.trashhunter.services.impl.TakerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class InitData {

    @Autowired
    OfferService offerService;

    @Autowired
    SenderService senderService;

    @Autowired
    TakerService takerService;

    private void init() {
        initSenders();
        initTakers();
        initOffers();
    }

    private void initSenders() {
        Sender sender1 = new Sender("sender1@mail.ru", "Mixa", "sender1", LocalDate.now());
        senderService.add(sender1);
        Sender sender2 = new Sender("sender2@mail.ru", "Max", "sender2", LocalDate.now());
        senderService.add(sender2);
        Sender sender3 = new Sender("sender3@mail.ru", "Ivan", "sender3", LocalDate.now());
        senderService.add(sender3);

    }

    private void initTakers() {
        Taker taker1 = new Taker("taker1@mail.ru", "Yura", "taker1",LocalDate.now());
        takerService.add(taker1);
        Taker taker2 = new Taker("taker2@mail.ru", "Matvey", "taker2",LocalDate.now());
        takerService.add(taker2);
        Taker taker3 = new Taker("taker3@mail.ru", "Denis", "taker3",LocalDate.now());
        takerService.add(taker3);

    }

    private void initOffers() {
        Offer offer1 = new Offer(senderService.getById(1L),2L,2L,35L,true,false, LocalDateTime.now(),"Hay");
        offerService.add(offer1);
    }
}
