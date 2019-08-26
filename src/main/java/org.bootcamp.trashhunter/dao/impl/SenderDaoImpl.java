package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.abstraction.SenderDao;
import org.bootcamp.trashhunter.models.Sender;
import org.springframework.stereotype.Repository;

@Repository("senderDao")
public class SenderDaoImpl extends AbstractDAOImpl<Sender> implements SenderDao {

}
