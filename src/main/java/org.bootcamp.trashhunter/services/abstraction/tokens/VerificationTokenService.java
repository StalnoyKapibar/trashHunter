package org.bootcamp.trashhunter.services.abstraction.tokens;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface VerificationTokenService extends BaseTokenService {

    public void completeRegistration(VerificationToken token);

    public void sendToken(User registeredUser);
}
