package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.abstraction.SenderDao;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SenderServiceImpl extends AbstractServiceImpl<Sender> implements SenderService {

    @Autowired
    private SenderDao senderDao;

}
