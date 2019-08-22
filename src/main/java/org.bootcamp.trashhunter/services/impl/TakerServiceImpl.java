package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.abstraction.TakerDao;
import org.bootcamp.trashhunter.models.Taker;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.abstraction.TakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TakerServiceImpl extends AbstractService<Taker> implements TakerService {

    @Autowired
    private TakerDao takerDaoImpl;

}
