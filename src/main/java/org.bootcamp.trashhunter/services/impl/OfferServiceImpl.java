package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.OfferDaoImpl;
import org.bootcamp.trashhunter.dao.impl.abstraction.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.OfferStatus;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.abstraction.OfferServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OfferServiceImpl extends AbstractService<Offer> implements OfferServiceI {

    @Autowired
    private OfferDao dao;

    @Override
    public List<Offer> getFilterQuery(Map<String, Object> map) {
        return dao.getFilterQuery(map);
    }

    @Override
    public List<Offer> getOffersBySenderId(Long id) {
        return dao.getOffersBySenderId(id);
    }

    @Override
    public void confirmOffer(Long id) {
        Offer offer = dao.getById(id);
        offer.setStatus(OfferStatus.COMPLETE);
        dao.update(offer);
    }
}
