package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.impl.abstraction.UserDao;
import org.bootcamp.trashhunter.models.User;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Repository
public class UserDaoImpl extends AbstractDAOImpl<User> implements UserDao {


    public User findByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email = :param", User.class)
                .setParameter("param", email)
                .getSingleResult();
    }


    public User findById(long id) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.id = :param", User.class)
                .setParameter("param", id)
                .getSingleResult();
    }


    public String base64Encoder(String string) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(
                string.getBytes(StandardCharsets.UTF_8) );
        return encodedString;
    }


}
