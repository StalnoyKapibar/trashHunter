package org.bootcamp.trashhunter.services.impl.tokens;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.dao.impl.token.VerificationTokenDAO;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.bootcamp.trashhunter.services.impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class VerificationTokenService extends BaseTokenService<VerificationToken> {

    @Autowired
    private MailService mailService;

    @Autowired
    public VerificationTokenService(VerificationTokenDAO verificationTokenDAO) {
        super(verificationTokenDAO);
    }

    public void completeRegistration(VerificationToken token) {
        token.getUser().setEnabled(true);
        abstractDAO.delete(token);
    }

    public void sendToken(User registeredUser){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                new VerificationToken(token, registeredUser, calculateExpiryDate());
        add(verificationToken);
        mailService.sendMessage(registeredUser, verificationToken);
    }
}
