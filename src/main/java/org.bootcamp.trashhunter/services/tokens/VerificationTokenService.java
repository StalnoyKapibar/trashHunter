package org.bootcamp.trashhunter.services.tokens;

import org.bootcamp.trashhunter.dao.token.VerificationTokenDAO;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VerificationTokenService extends BaseTokenService<VerificationToken> {

    @Autowired
    public VerificationTokenService(VerificationTokenDAO verificationTokenDAO) {
        super(verificationTokenDAO);
    }

    public void completeRegistration(VerificationToken token) {
        token.getUser().setEnabled(true);
        abstractDAO.delete(token);
    }
}
