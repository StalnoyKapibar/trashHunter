package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OfferService extends AbstractService<Offer> {

    @Autowired
    private OfferDao dao;

    @Autowired
    private TakerService takerService;

    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return dao.getFilterQuery(map);
    }

    public Map<Offer,List<Taker>> getOffersBySenderIdActiveFirst(Long id) {
        return dao.getOffersBySenderIdActiveFirst(id);
    }

    public void confirmOffer(Long takerId, Long offerId) {
        Offer offer = dao.getById(offerId);
        offer.setOfferStatus(OfferStatus.TAKEN);
        List<Taker> takers = new ArrayList<>();
        takers.add(takerService.getById(takerId));
        offer.setRespondingTakers(takers);
        // оповестить тейкера в чате
        dao.update(offer);
    }

    public void cancelOffer(Long offerId){
        Offer offer = dao.getById(offerId);
        offer.setOfferStatus(OfferStatus.OPEN);
        offer.setRespondingTakers(new ArrayList<>());
        dao.update(offer);
    }

    public void makeCompleteOffer(Long offerId){
        Offer offer = dao.getById(offerId);
        offer.setOfferStatus(OfferStatus.COMPLETE);
        offer.setRespondingTakers(new ArrayList<>());
        dao.update(offer);
    }

    public void restoreOffer(Long offerId){
        Offer offer = dao.getById(offerId);
        offer.setOfferStatus(OfferStatus.OPEN);
        dao.update(offer);
    }
}
