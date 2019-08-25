package org.bootcamp.trashhunter.services.impl.tokens;

import org.bootcamp.trashhunter.dao.abstraction.BaseTokenDAO;
import org.bootcamp.trashhunter.dao.abstraction.VerificationTokenDAO;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.BaseToken;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.abstraction.MailService;
import org.bootcamp.trashhunter.services.abstraction.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void sendToken(User registeredUser){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                new VerificationToken(token, registeredUser, calculateExpiryDate());
        baseTokenDAO.add(verificationToken);
        mailService.sendMessage(registeredUser, verificationToken);
    }
}
