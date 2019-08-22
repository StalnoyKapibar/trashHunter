package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.User;

public interface UserServiceI extends AbstractServiceI<User> {
    User findByEmail(String email);

    User findById(long id);

    String encoder64(String string);
}
