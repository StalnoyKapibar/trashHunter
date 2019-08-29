package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDAOImpl<User> implements UserDao {

    public List<User> getUsersFriendsListByUsersId(List<Long> id) {
        Query query = entityManager.createQuery("from User where id IN (:paramId)");
        query.setParameter("paramId", id);
        return (List<User>) query.getResultList();
    }

    public User findByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email = :param", User.class)
                .setParameter("param", email).getResultStream().findFirst().orElse(null);
    }


    public User findById(long id) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.id = :param", User.class)
                .setParameter("param", id)
                .getSingleResult();
    }


    public String base64Encoder(String string) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(string.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void setLimit(long id, int limit) {
        entityManager.createQuery("Update User u set u.limit=:limit WHERE u.id=:id")
                .setParameter("id", id)
                .setParameter("limit", limit)
                .executeUpdate();
    }


}
