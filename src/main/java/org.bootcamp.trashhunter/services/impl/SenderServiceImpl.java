package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.SenderDaoImpl;
import org.bootcamp.trashhunter.dao.impl.abstraction.SenderDao;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.abstraction.SenderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SenderServiceImpl extends AbstractService<Sender> implements SenderServiceI {

    @Autowired
    private SenderDao senderDao;

}
