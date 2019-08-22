package org.bootcamp.trashhunter.dao.impl.token;

import org.bootcamp.trashhunter.dao.impl.AbstractDAOImpl;
import org.bootcamp.trashhunter.models.token.BaseToken;

public class BaseTokenDAOImpl<T extends BaseToken> extends AbstractDAOImpl<T> {

    public T findByToken(String token) {
        return entityManager
                .createQuery("SELECT t FROM " + clazz.getName() + " t WHERE t.token = :param", clazz)
                .setParameter("param", token)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    public boolean existsTokenByUserId(Long userId) {
        return !entityManager
                .createQuery("SELECT t FROM " + clazz.getName() + " t WHERE t.user.id = :id", clazz)
                .setParameter("id", userId)
                .getResultList().isEmpty();
    }

    public T findTokenByUserId(Long userId) {
        return entityManager
                .createQuery("SELECT t FROM " + clazz.getName() + " t WHERE t.user.id = :id", clazz)
                .setParameter("id", userId)
                .getResultList()
                .stream().findFirst().orElse(null);
    }
}