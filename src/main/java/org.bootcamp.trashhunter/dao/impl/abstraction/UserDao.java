package org.bootcamp.trashhunter.dao.impl.abstraction;

import org.bootcamp.trashhunter.models.User;

public interface UserDao extends AbstractDao<User> {
    User findByEmail(String email);

    User findById(long id);

    String base64Encoder(String string);
}
