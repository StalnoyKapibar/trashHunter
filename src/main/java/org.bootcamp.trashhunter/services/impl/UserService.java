package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.UserDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User> {

    @Autowired
    private UserDao dao;

    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    public String encoder64(String string) { return  dao.base64Encoder(string);
    }
}
