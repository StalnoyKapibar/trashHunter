package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.bootcamp.trashhunter.models.User;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository("userDao")
public class UserDao extends AbstractDAO<User> {

	public List<User> getUsersFriendsListByUsersId(List<Long> id) {
		//HQL
		Query query = entityManager.createQuery("from User where id IN (:paramId)");
		query.setParameter("paramId", id);
		List<User> list = query.getResultList();
		return list;
	}

    public User findByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email = :param", User.class)
                .setParameter("param", email)
                .getSingleResult();
    }

}
