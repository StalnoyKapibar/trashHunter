package org.bootcamp.trashhunter.config;


import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.TrashType;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.models.embedded.Coordinates;

import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.SenderService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.bootcamp.trashhunter.services.impl.UserFavoritesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class InitData {

    @Autowired
    OfferService offerService;

    @Autowired
    SenderService senderService;

    @Autowired
    TakerService takerService;

	@Autowired
    UserFavoritesServiceImpl userFavoritesService;

    private void init() {
        initSenders();
        initTakers();
        initRandomOffers(40);
		initUserFavorites();
    }

    private void initUserFavorites() {
        UserFavorites uf1 = new UserFavorites(1L, 4L);
        userFavoritesService.add(uf1);
        UserFavorites uf2 = new UserFavorites(1L, 5L);
        userFavoritesService.add(uf2);
        UserFavorites uf3 = new UserFavorites(2L, 5L);
        userFavoritesService.add(uf3);
        UserFavorites uf4 = new UserFavorites(6L, 1L);
        userFavoritesService.add(uf4);
        UserFavorites uf5 = new UserFavorites(1L, 6L);
        userFavoritesService.add(uf5);
        UserFavorites uf6 = new UserFavorites(5L, 2L);
        userFavoritesService.add(uf6);
    }

    private void initSenders() {
        Sender sender1 = new Sender("sender1@mail.ru", "Mixa", "sender1", LocalDate.now(), "Выборг, Россия");
        senderService.add(sender1);
        Sender sender2 = new Sender("sender2@mail.ru", "Max", "sender2", LocalDate.now(), "Выборг, Россия");
        senderService.add(sender2);
        Sender sender3 = new Sender("sender3@mail.ru", "Ivan", "sender3", LocalDate.now(), "Выборг, Россия");
        senderService.add(sender3);
    }

    private void initTakers() {
        Taker taker1 = new Taker("taker1@mail.ru", "Yura", "taker1", LocalDate.now(), "Выборг, Россия");
        takerService.add(taker1);
        Taker taker2 = new Taker("taker2@mail.ru", "Matvey", "taker2", LocalDate.now(), "Выборг, Россия");
        takerService.add(taker2);
        Taker taker3 = new Taker("taker3@mail.ru", "Denis", "taker3", LocalDate.now(), "Выборг, Россия");
        takerService.add(taker3);
    }

    private void initRandomOffers(int quantity) {
        double seed;
        double seed1;
        double seed2;

        Sender randomSender;
        long randomWeight;
        long randomVolume;
        long randomPrice;
        TrashType randomTrashType;
        boolean randomIsSorted;
        OfferStatus randomStatus;
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
            seed1 = Math.random();
            seed2 = Math.random();

            randomSender = senderService.getById(1 + (long) (seed * numOfSenders));
            randomWeight = (long) (seed * maxWeight);
            randomVolume = (long) (seed * maxVolume);
            randomPrice = (long) (seed * maxPrice);
            randomTrashType = TrashType.getRandom();
            randomIsSorted = seed < 0.5;
            randomStatus = OfferStatus.getRandom();
            randomDate = LocalDateTime.now();
            randomDescription = "this is offer number " + i;
            randomLatitude = minLatitude + seed * (maxLatitude - minLatitude);
            randomLongitude = minLongitude + seed1 * (maxLongitude - minLongitude);
            randomCoordinates = new Coordinates(randomLatitude, randomLongitude);

            Offer randomOffer = new Offer(randomSender, randomWeight, randomVolume, randomPrice, randomTrashType,
                    randomIsSorted, randomStatus, randomDate, randomDescription, randomCoordinates);
            if (randomOffer.getOfferStatus().equals(OfferStatus.ACTIVE)) {
                randomOffer.setRespondingTakers(takerService.getAll());
            }
            if (randomOffer.getOfferStatus().equals(OfferStatus.TAKEN)){
                List<Taker> takers = new ArrayList<>();
                takers.add(takerService.getById(4L));
                randomOffer.setRespondingTakers(takers);
            }
            offerService.add(randomOffer);
        }
    }
}
