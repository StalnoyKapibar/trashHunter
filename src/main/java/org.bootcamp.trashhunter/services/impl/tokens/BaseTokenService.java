//package org.bootcamp.trashhunter.services.impl.tokens;
//
//
//
//import org.bootcamp.trashhunter.dao.impl.abstraction.BaseTokenDAO;
//import org.bootcamp.trashhunter.models.token.BaseToken;
//import org.bootcamp.trashhunter.services.AbstractService;
//
//import java.util.Calendar;
//import java.util.Date;
//
//public abstract class BaseTokenService<T extends BaseToken> extends AbstractService<T> {
//
//    protected final BaseTokenDAO<T> baseTokenDAO;
//
//    public BaseTokenService(BaseTokenDAO<T> baseTokenDAO) {
//        this.baseTokenDAO = baseTokenDAO;
//    }
//
//    public Date calculateExpiryDate() {
//        int periodInDays = 1;
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, periodInDays);
//        return calendar.getTime();
//    }
//
//    public T findByToken(String token) {
//        return baseTokenDAO.findByToken(token);
//    }
//
//    public T findTokenByUserId(Long userId) {
//        return baseTokenDAO.findTokenByUserId(userId);
//    }
//
//    public boolean existsTokenByUserId(Long userID) {
//        return baseTokenDAO.existsTokenByUserId(userID);
//    }
//
//    public boolean tokenIsNonExpired(T token) {
//        Calendar calendar = Calendar.getInstance();
//        boolean isNonExpired = (token.getExpiryDate().getTime() - calendar.getTime().getTime()) >= 0;
//        if (!isNonExpired) {
//            abstractDAOImpl.delete(token);
//        }
//        return isNonExpired;
//    }
//}
