package org.bootcamp.trashhunter.services.impl;

import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;

    public User findByEmail(String email) {
        return  userDao.findByEmail(email);
    }

    public User findById(long id) {
        return  userDao.findById(id);
    }

    public String encoder64(String string) { return   userDao.base64Encoder(string);
    }

    public boolean isValid(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (findByEmail(email) == null && email.matches(regex)) {
            return true;
        }
        return false;
    }
}
