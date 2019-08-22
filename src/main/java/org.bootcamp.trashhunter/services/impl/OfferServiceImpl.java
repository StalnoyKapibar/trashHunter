package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.abstraction.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.abstraction.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OfferServiceImpl extends AbstractService<Offer> implements OfferService {

    @Autowired
    private OfferDao offerDao;

    @Override
    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return offerDao.getFilterQuery(map);
    }

    public Map<Offer,List<Taker>> getOffersBySenderIdActiveFirst(Long id) {
        return offerDao.getOffersBySenderIdActiveFirst(id);
    }

    @Override
    public void confirmOffer(Long id) {
        Offer offer = offerDao.getById(id);
        offer.setStatus(OfferStatus.COMPLETE);
        offerDao.update(offer);
    }
}
