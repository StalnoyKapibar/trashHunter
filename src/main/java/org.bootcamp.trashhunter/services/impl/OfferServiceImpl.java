package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.abstraction.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
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
    private TakerService takerService;

    @Override
    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return offerDao.getFilterQuery(map);
    }

    @Override
    public List<Offer> getOffersByTaker(String email){
        return offerDao.getOffersByTaker(email);
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
    public void restoreOffer(Long offerId){
        Offer offer = offerDao.getById(offerId);
        offer.setOfferStatus(OfferStatus.OPEN);
        offerDao.update(offer);
    }
}
