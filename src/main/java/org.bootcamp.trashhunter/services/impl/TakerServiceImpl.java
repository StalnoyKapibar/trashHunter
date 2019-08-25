package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.abstraction.TakerDao;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TakerServiceImpl extends AbstractServiceImpl<Taker> implements TakerService {

    @Autowired
    private TakerDao takerDaoImpl;

}
