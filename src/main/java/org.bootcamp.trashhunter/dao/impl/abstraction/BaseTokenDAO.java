package org.bootcamp.trashhunter.dao.impl.abstraction;

import org.bootcamp.trashhunter.models.token.BaseToken;

public interface BaseTokenDAO<T extends BaseToken> extends AbstractDao<BaseToken>  {
    T findByToken(String token);

    boolean existsTokenByUserId(Long userId);

    T findTokenByUserId(Long userId);
}
