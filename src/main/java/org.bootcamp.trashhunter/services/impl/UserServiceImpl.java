package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.impl.UserDaoImpl;
import org.bootcamp.trashhunter.dao.impl.abstraction.UserDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractService;
import org.bootcamp.trashhunter.services.abstraction.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserServiceI {

    @Autowired
    private UserDao dao;

    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    public User findById(long id) {
        return dao.findById(id);
    }

    public String encoder64(String string) { return  dao.base64Encoder(string);
    }
}