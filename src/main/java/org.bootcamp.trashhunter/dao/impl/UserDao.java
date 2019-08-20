package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.bootcamp.trashhunter.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDAO<User> {

    public User findByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email = :param", User.class)
                .setParameter("param", email)
                .getSingleResult();
    }
}
