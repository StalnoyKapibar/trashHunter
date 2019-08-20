package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OfferService extends AbstractService<Offer> {

    @Autowired
    private OfferDao dao;

    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return dao.getFilterQuery(map);
    }

    public List<Offer> getOffersBySenderId(Long id) {
        return dao.getOffersBySenderId(id);
    }

    public void confirmOffer(Long id) {
        Offer offer = dao.getById(id);
        offer.setActive(false);
        offer.setClosed(true);
        dao.update(offer);
    }
    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return dao.getFilterQuery(map);
    }


}
