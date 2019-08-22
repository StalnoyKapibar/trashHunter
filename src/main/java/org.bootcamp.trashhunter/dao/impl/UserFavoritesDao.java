package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userFavoritesDao")
public class UserFavoritesDao extends AbstractDAO<UserFavorites> {

	public List<UserFavorites> getAllUserFavById(Long id) {
		return entityManager.createNativeQuery("SELECT * FROM userfavorites WHERE userboss_id=" + id, clazz).getResultList();
	}

}
