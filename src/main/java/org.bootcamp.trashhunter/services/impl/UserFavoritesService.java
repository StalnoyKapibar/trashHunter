package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.UserFavoritesDao;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserFavoritesService extends AbstractService<UserFavorites> {

	@Autowired
	UserFavoritesDao dao;

}
