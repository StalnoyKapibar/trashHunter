package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.abstraction.UserFavoritesDao;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userFavoritesDao")
public class UserFavoritesDaoImpl extends AbstractDAOImpl<UserFavorites> implements UserFavoritesDao {

    @Override
	public List<UserFavorites> getAllUserFavById(Long id) {
		return entityManager.createNativeQuery("SELECT * FROM userfavorites WHERE userboss_id=" + id, clazz).getResultList();
	}

}
