package org.bootcamp.trashhunter.services.impl.tokens;

import org.bootcamp.trashhunter.dao.impl.token.VerificationTokenDAOImpl;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VerificationTokenService extends BaseTokenService<VerificationToken> {

    @Autowired
    public VerificationTokenService(VerificationTokenDAOImpl verificationTokenDAO) {
        super(verificationTokenDAO);
    }

    public void completeRegistration(VerificationToken token) {
        token.getUser().setEnabled(true);
        abstractDAOImpl.delete(token);
    }
}
