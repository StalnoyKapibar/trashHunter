package org.bootcamp.trashhunter.config;

import org.bootcamp.trashhunter.embedded.Coordinates;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.TrashType;
import org.bootcamp.trashhunter.services.impl.OfferService;
import org.bootcamp.trashhunter.services.impl.SenderService;
import org.bootcamp.trashhunter.services.impl.TakerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;


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
        initRandomOffers(40);
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
        Taker taker1 = new Taker("taker1@mail.ru", "Yura", "taker1", LocalDate.now());
        takerService.add(taker1);
        Taker taker2 = new Taker("taker2@mail.ru", "Matvey", "taker2", LocalDate.now());
        takerService.add(taker2);
        Taker taker3 = new Taker("taker3@mail.ru", "Denis", "taker3", LocalDate.now());
        takerService.add(taker3);

    }

    private void initOffers() {
        Offer offer1 = new Offer(senderService.getById(1L), 2L, 2L, 35L, TrashType.METAL, true, false,
                LocalDateTime.now(), "Hay", new Coordinates(33.3, 55.5));
        offerService.add(offer1);
    }

    private void initRandomOffers(int quantity) {
        double seed;
        double seed2;
        Sender randomSender;
        long randomWeight;
        long randomVolume;
        long randomPrice;
        TrashType randomTrashType;
        boolean randomIsSorted;
        boolean randomIsClosed;
        LocalDateTime randomDate;
        String randomDescription;
        double randomLatitude;
        double randomLongitude;
        Coordinates randomCoordinates;

        long numOfSenders = senderService.getAll().size();
        double maxWeight = 1000.0;
        double maxVolume = 100.0;
        double maxPrice = 100000.0;
        double maxLatitude = 60.71519508788199;
        double minLatitude = 60.689554174264735;
        double maxLongitude = 28.78891464544222;
        double minLongitude = 28.728941036284482;

        for (int i = 0; i < quantity; i++) {
            seed = Math.random();
            seed2 = Math.random();

            randomSender = senderService.getById(1 + (long) (seed * numOfSenders));
            randomWeight = (long) (seed * maxWeight);
            randomVolume = (long) (seed * maxVolume);
            randomPrice = (long) (seed * maxPrice);
            randomTrashType = TrashType.getRandom();
            randomIsSorted = seed < 0.5;
            randomIsClosed = false;
            randomDate = LocalDateTime.now();
            randomDescription = "this is offer number " + i;
            randomLatitude = minLatitude + seed * (maxLatitude - minLatitude);
            randomLongitude = minLongitude + seed2 * (maxLongitude - minLongitude);
            randomCoordinates = new Coordinates(randomLatitude, randomLongitude);

            Offer randomOffer = new Offer(randomSender, randomWeight, randomVolume, randomPrice, randomTrashType,
                    randomIsSorted, randomIsClosed, randomDate, randomDescription, randomCoordinates);
            offerService.add(randomOffer);
        }
    }
}
