package org.bootcamp.trashhunter.services.impl.tokens;

import org.bootcamp.trashhunter.dao.abstraction.BaseTokenDAO;
import org.bootcamp.trashhunter.models.token.BaseToken;
import org.bootcamp.trashhunter.services.AbstractServiceImpl;
import org.bootcamp.trashhunter.services.abstraction.tokens.BaseTokenService;
import java.util.Calendar;
import java.util.Date;

public abstract class BaseTokenServiceImpl<T extends BaseToken> extends AbstractServiceImpl<T> implements BaseTokenService<T> {

    protected final BaseTokenDAO<T> baseTokenDAO;

    public BaseTokenServiceImpl(BaseTokenDAO<T> baseTokenDAO) {
        this.baseTokenDAO = baseTokenDAO;
    }

    @Override
    public Date calculateExpiryDate() {
        int periodInDays = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, periodInDays);
        return calendar.getTime();
    }

    @Override
    public T findByToken(String token) {
        return baseTokenDAO.findByToken(token);
    }

    @Override
    public T findTokenByUserId(Long userId) {
        return baseTokenDAO.findTokenByUserId(userId);
    }

    @Override
    public boolean existsTokenByUserId(Long userID) {
        return baseTokenDAO.existsTokenByUserId(userID);
    }

    @Override
    public boolean tokenIsNonExpired(T token) {
        Calendar calendar = Calendar.getInstance();
        boolean isNonExpired = (token.getExpiryDate().getTime() - calendar.getTime().getTime()) >= 0;
        if (!isNonExpired) {
            abstractDAO.delete(token);
        }
        return isNonExpired;
    }
}
