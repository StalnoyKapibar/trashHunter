package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.SenderDao;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SenderService extends AbstractService<Sender> {

    @Autowired
    private SenderDao dao;

}
