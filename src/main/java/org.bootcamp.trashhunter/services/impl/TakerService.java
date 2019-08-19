package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.TakerDao;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TakerService extends AbstractService<Taker> {

    @Autowired
    private TakerDao dao;

}
