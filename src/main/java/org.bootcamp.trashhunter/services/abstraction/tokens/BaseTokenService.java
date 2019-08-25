package org.bootcamp.trashhunter.services.abstraction.tokens;

import org.bootcamp.trashhunter.models.token.BaseToken;
import org.bootcamp.trashhunter.services.abstraction.AbstractService;

import java.util.Date;

public interface BaseTokenService<T extends BaseToken> extends AbstractService<T> {

    Date calculateExpiryDate();

    T findByToken(String token);

    T findTokenByUserId(Long userId);

    boolean existsTokenByUserId(Long userID);

    boolean tokenIsNonExpired(T token);

}
