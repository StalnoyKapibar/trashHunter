package org.bootcamp.trashhunter.dao.abstraction;

import org.bootcamp.trashhunter.models.User;

import java.util.List;

public interface UserDao extends AbstractDao<User> {

    public List<User> getUsersFriendsListByUsersId(List<Long> id);

    User findByEmail(String email);

    User findById(long id);

    String base64Encoder(String string);

    void setLimit(long id, int limit);
}
