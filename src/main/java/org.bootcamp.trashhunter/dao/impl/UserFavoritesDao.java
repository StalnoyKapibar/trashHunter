package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.springframework.stereotype.Repository;

@Repository("userFavoritesDao")
public class UserFavoritesDao extends AbstractDAO<UserFavorites> {}
