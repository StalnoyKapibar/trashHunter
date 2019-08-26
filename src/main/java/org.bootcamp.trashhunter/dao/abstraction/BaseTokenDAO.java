package org.bootcamp.trashhunter.dao.abstraction;

import org.bootcamp.trashhunter.models.token.BaseToken;

public interface BaseTokenDAO<T extends BaseToken> extends AbstractDao<T>  {

    T findByToken(String token);

    boolean existsTokenByUserId(Long userId);

    T findTokenByUserId(Long userId);
}
