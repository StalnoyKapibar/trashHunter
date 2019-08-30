package org.bootcamp.trashhunter.config;

import com.github.javafaker.Faker;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.models.TrashType;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.models.*;
import org.bootcamp.trashhunter.models.embedded.Coordinates;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.SenderService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.bootcamp.trashhunter.services.abstraction.VoteService;
import org.bootcamp.trashhunter.services.impl.UserFavoritesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class InitData {

    @Autowired
    OfferService offerService;

    @Autowired
    SenderService senderService;

    @Autowired
    TakerService takerService;

    @Autowired
    UserFavoritesServiceImpl userFavoritesService;

    @Autowired
    UserService userService;

    private Faker faker = new Faker(new Locale("ru"));


    @Autowired
    VoteService voteService;

    private void init() {
        initSenders();
        initTakers();
        initRandomOffers(100);
		initUserFavorites();
        initRandomOffers(40);
        initUserFavorites();
        initVotes();
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
        UserFavorites uf7 = new UserFavorites(4L, 2L);
        userFavoritesService.add(uf7);
    }

    private void initSenders() {
        Statistics s1 = new Statistics(0, 0, 0, 0);
        Statistics s2 = new Statistics(20, 100, 28, 860);
        Statistics s3 = new Statistics(30, 200, 22, 121);
        Statistics s4 = new Statistics(300, 2000, 2203, 1211);
        Sender sender1 = new Sender("sender1@mail.ru", "Михаил А.", "sender1", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("mixa.jpg"));
        sender1.setAddress(faker.address().streetName());
        sender1.setAboutUser(faker.lebowski().quote());
        sender1.setStatistics(s1);
        senderService.add(sender1);
        Sender sender2 = new Sender("sender2@mail.ru", "Максим В.", "sender2", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("max.jpg") );
        sender2.setAddress(faker.address().streetName());
        sender2.setAboutUser(faker.lebowski().quote());
        sender2.setStatistics(s2);
        senderService.add(sender2);
        Sender sender3 = new Sender("sender3@mail.ru", "Иван Ф.", "sender3", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("ivan.jpg"));
        sender3.setAddress(faker.address().streetName());
        sender3.setAboutUser(faker.lebowski().quote());
        sender3.setStatistics(s3);
        senderService.add(sender3);
        Sender sender4 = new Sender("sender4@mail.ru", "Юрий П.", "sender4", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("iura.jpg"));
        sender4.setAddress(faker.address().streetName());
        sender4.setStatistics(s4);
        sender4.setAboutUser(faker.lebowski().quote());
        senderService.add(sender4);
        for (int i = 0; i < 15; i++) {
            Sender sender = new Sender("send" + i + "@mail.ru",faker.name().firstName() + " " + faker.name().lastName(),
                    "sender" + i, LocalDate.now(),"Viborg, Russia",
                    userService.extractBytesDefaultAvatar("l" + i + ".jpg"));
                    sender.setAddress(faker.address().streetAddress());
                    sender.setAboutUser(faker.lebowski().quote());
                    Statistics s = new Statistics(10, 50, 12, 240);
                    sender.setStatistics(s);
                    senderService.add(sender);
        }
    }

    private void initTakers() {
        Statistics s1 = new Statistics(0, 0, 0, 0);
        Statistics s2 = new Statistics(20, 100, 28, 860);
        Statistics s3 = new Statistics(30, 200, 22, 121);

        Taker taker1 = new Taker("taker1@mail.ru", "Юра З.", "taker1", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("yura.jpg"));
        taker1.setAddress(faker.address().streetName());
        taker1.setAboutUser(faker.lebowski().quote());
        taker1.setStatistics(s1);
        takerService.add(taker1);
        Taker taker2 = new Taker("taker2@mail.ru", "Матвей О.", "taker2", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("matey.jpg"));
        taker2.setAddress(faker.address().streetName());
        taker2.setAboutUser(faker.lebowski().quote());
        taker2.setAboutUser(faker.lebowski().quote());
        taker2.setStatistics(s2);
        takerService.add(taker2);
        Taker taker3 = new Taker("taker3@mail.ru", "Денис Т.", "taker3", LocalDate.now(), "Viborg, Russia", userService.extractBytesDefaultAvatar("denis.jpg"));
        taker3.setAddress(faker.address().streetName());
        taker3.setAboutUser(faker.lebowski().quote());
        taker3.setStatistics(s3);
        takerService.add(taker3);
        for (int i = 0; i < 15; i++) {
            Taker taker = new Taker("take" + i + "@mail.ru",faker.name().firstName() + " " + faker.name().lastName(),
                                    "taker" + i, LocalDate.now(),"Viborg, Russia",
                                    userService.extractBytesDefaultAvatar(i+".jpg"));
              taker.setAboutUser(faker.lebowski().quote());
              taker.setAddress(faker.address().streetName());
              Statistics s = new Statistics(10, 50, 12, 240);
              taker.setStatistics(s);
              takerService.add(taker);
        }

    }

    private void initRandomOffers(int quantity) {

        double seed1;
        double seed2;
        double seed3;

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
            seed1 = Math.random();
            seed2 = Math.random();
            seed3 = Math.random();

            randomSender = senderService.getById(1 + (long) (seed1 * numOfSenders));
            randomWeight = (long) (seed1 * maxWeight);
            randomVolume = (long) (seed2 * maxVolume);
            randomPrice = (long) (seed3 * maxPrice);
            randomTrashType = TrashType.getRandom();
            randomIsSorted = seed1 < 0.5;
            randomStatus = OfferStatus.getRandom();
            randomDate = LocalDateTime.now();
            randomDescription = "this is offer number " + i;
            randomLatitude = minLatitude + seed2 * (maxLatitude - minLatitude);
            randomLongitude = minLongitude + seed3 * (maxLongitude - minLongitude);
            randomCoordinates = new Coordinates(randomLatitude, randomLongitude);
            Random random = new Random();

            Offer randomOffer = new Offer(randomSender, randomWeight, randomVolume, randomPrice, randomTrashType,
                    randomIsSorted, randomStatus, randomDate, randomDescription, randomCoordinates);
            if (randomOffer.getOfferStatus().equals(OfferStatus.ACTIVE)) {

                List<Taker> allTakers = takerService.getAll();
                Collections.shuffle(allTakers);
                List<Taker> takersForRandomOffer = new ArrayList<>();
                for (int j = 0; j < (int)(Math.random()*10 + 2); j++) {
                    takersForRandomOffer.add(allTakers.get(j));

                }
                randomOffer.setRespondingTakers(takersForRandomOffer);
            }
            if (randomOffer.getOfferStatus().equals(OfferStatus.TAKEN)) {
                List<Taker> takers = new ArrayList<>();
                takers.add(takerService.getById(20L));
                randomOffer.setRespondingTakers(takers);
            }
            if (randomOffer.getOfferStatus().equals(OfferStatus.COMPLETE)) {
                List<Taker> takers = new ArrayList<>();
                takers.add(takerService.getById(21L));
                randomOffer.setRespondingTakers(takers);
            }
            offerService.add(randomOffer);
        }
    }

    private void initVotes() {
        Vote vote;
        for (int i=5; i<=7; i++) {
            vote = new Vote(i, 4, Math.random() > 0.5);
            voteService.add(vote);
            vote = new Vote(4, i, Math.random() > 0.5);
            voteService.add(vote);
            for (int j=1; j<=3; j++) {
                vote = new Vote(i, j, Math.random() > 0.35);
                voteService.add(vote);
                vote = new Vote(j, i, Math.random() > 0.35);
                voteService.add(vote);
            }
        }
    }
}
