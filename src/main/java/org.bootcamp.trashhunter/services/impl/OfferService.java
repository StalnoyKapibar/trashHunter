package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfferService extends AbstractService<Offer> {

    @Autowired
    private OfferDao dao;

}
