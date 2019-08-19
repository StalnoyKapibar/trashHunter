package org.bootcamp.trashhunter.dao.token;

import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.bootcamp.trashhunter.models.token.BaseToken;

public class BaseTokenDAO<T extends BaseToken> extends AbstractDAO<T> {

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