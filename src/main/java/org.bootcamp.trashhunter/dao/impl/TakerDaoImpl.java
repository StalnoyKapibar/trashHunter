package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.abstraction.TakerDao;
import org.bootcamp.trashhunter.models.Taker;
import org.springframework.stereotype.Repository;

@Repository("takerDao")
public class TakerDaoImpl extends AbstractDAOImpl<Taker> implements TakerDao {

}
