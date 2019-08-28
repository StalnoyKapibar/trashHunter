package org.bootcamp.trashhunter.services.impl.tokens;

import org.bootcamp.trashhunter.dao.abstraction.BaseTokenDAO;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.abstraction.MailService;
import org.bootcamp.trashhunter.services.abstraction.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenServiceImpl extends BaseTokenServiceImpl<VerificationToken> implements VerificationTokenService {

    @Autowired
    private MailService mailService;

    @Autowired
    public VerificationTokenServiceImpl(BaseTokenDAO<VerificationToken> baseTokenDAO) {
        super(baseTokenDAO);
    }

    public void completeRegistration(VerificationToken token) {
        token.getUser().setEnabled(true);
        baseTokenDAO.delete(token);
    }

    public void sendToken(User registeredUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                new VerificationToken(token, registeredUser, calculateExpiryDate());
        baseTokenDAO.add(verificationToken);
        Map<String, Object> map = getMessageMap(registeredUser.getName(),
                "Благодарим за регистрацию на нашем сервисе. Пожалуйста, перейдите по ссылке снизу: ",
                String.format("http://localhost:8080/activate/%s", verificationToken.getToken()));
        mailService.sendMessageWithModel(registeredUser.getEmail(), "Activation code", map);
    }

    @Override
    public void sendTokenToResetPassword(User registeredUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                new VerificationToken(token, registeredUser, calculateExpiryDate());
        baseTokenDAO.add(verificationToken);
        Map<String, Object> map = getMessageMap(registeredUser.getName(),
                "Для восстановления пароля пройдите по ссылке внизу: ",
                String.format("http://localhost:8080/reset/get_token/%s", verificationToken.getToken()));
        mailService.sendMessageWithModel(registeredUser.getEmail(), "Reset password", map);
    }

    private Map<String, Object> getMessageMap(String userName, String body, String link) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("body", body);
        map.put("link", link);
        return map;
    }
}
