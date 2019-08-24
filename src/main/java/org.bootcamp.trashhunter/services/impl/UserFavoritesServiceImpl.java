package org.bootcamp.trashhunter.services.impl;


import org.bootcamp.trashhunter.dao.abstraction.UserFavoritesDao;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.UserFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserFavoritesServiceImpl extends AbstractServiceImpl<UserFavorites> implements UserFavoritesService {

	@Autowired
    UserFavoritesDao dao;

}
