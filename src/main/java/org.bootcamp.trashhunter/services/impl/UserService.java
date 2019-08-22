package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.UserDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends AbstractService<User> {

	@Autowired
	private UserDao dao;

}