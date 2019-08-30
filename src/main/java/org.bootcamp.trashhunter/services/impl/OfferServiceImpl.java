package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.abstraction.OfferDao;

import org.bootcamp.trashhunter.dao.abstraction.StatisticsDao;
import org.bootcamp.trashhunter.models.*;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OfferServiceImpl extends AbstractServiceImpl<Offer> implements OfferService {

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private TakerService takerService;

    @Autowired
    private UserService userService;

    @Override
    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return offerDao.getFilterQuery(map);
    }

    @Override
    public List<Offer> getOffersByTaker(String email){
        return offerDao.getOffersByTaker(email);
    }

    @Override
    public List<Offer> getTakenOffersByTaker(Taker taker) {
        return offerDao.getTakenOffersByTaker(taker);
    }

    @Override
    public List<Offer> getTakenOffersBySender(Sender sender) {
        return offerDao.getTakenOffersBySender(sender);
    }

    @Override
    public List<Offer> getFilterOffersForTaker(Map<String, Object> map,String email){
        return  offerDao.getFilterOffersForTaker(map,email);
    }

    @Override
    public void takingOfferByTaker(Long takerId, Long offerId) {
        Offer offer = offerDao.getById(offerId);
        offer.setOfferStatus(OfferStatus.ACTIVE);
        List<Taker> takers = offer.getRespondingTakers();
        takers.add(takerService.getById(takerId));
        offer.setRespondingTakers(takers);
        offerDao.update(offer);

    }

    @Override
    public Map<Offer,List<Taker>> getOffersBySenderIdActiveFirst(String email) {
        return offerDao.getOffersBySenderIdActiveFirst(email);
    }

    @Override
    public void confirmOffer(Long takerId, Long offerId) {
        Offer offer = offerDao.getById(offerId);
        offer.setOfferStatus(OfferStatus.TAKEN);
        List<Taker> takers = new ArrayList<>();
        takers.add(takerService.getById(takerId));
        offer.setRespondingTakers(takers);
        // оповестить тейкера в чате
        offerDao.update(offer);
    }

    @Override
    public void cancelOffer(Long offerId){
        Offer offer = offerDao.getById(offerId);
        offer.setOfferStatus(OfferStatus.OPEN);
        offer.setRespondingTakers(new ArrayList<>());
        offerDao.update(offer);
    }

    @Override
    public void makeCompleteOffer(Long offerId){
        Offer offer = offerDao.getById(offerId);
        offer.setOfferStatus(OfferStatus.COMPLETE);
        offer.setRespondingTakers(new ArrayList<>());
        offerDao.update(offer);
    }

    @Override
    public void makeCompleteOfferByTaker(Long offerId){
        List<User> users = new ArrayList<>();
        Offer offer = offerDao.getById(offerId);
        users.add(offer.getRespondingTakers().get(0));
        users.add(offer.getSender());
        for (User user : users) {
            Statistics statistics = user.getStatistics();
            statistics.setNumOfDeals(statistics.getNumOfDeals() + 1);
            statistics.setSummaryWeight(statistics.getSummaryWeight()+offer.getWeight());
            statisticsDao.update(statistics);
        }
        offer.setOfferStatus(OfferStatus.COMPLETE);
        offerDao.update(offer);
    }

    @Override
    public void rateOffer(Long senderId, Long offerId, Integer rating){
        User user = userService.getById(senderId);
        Statistics statistics = user.getStatistics();
        statistics.setSummaryScore(statistics.getSummaryScore() + rating);
        statistics.setNumOfRatings(statistics.getNumOfRatings() + 1);
        userService.update(user);
        if (user.getClass() == Taker.class) {
            Offer offer = offerDao.getById(offerId);
            offer.setRespondingTakers(new ArrayList<>());
            offerDao.update(offer);
        }
    }

    @Override
    public void restoreOffer(Long offerId){
        Offer offer = offerDao.getById(offerId);
        offer.setOfferStatus(OfferStatus.OPEN);
        offerDao.update(offer);
    }
}
