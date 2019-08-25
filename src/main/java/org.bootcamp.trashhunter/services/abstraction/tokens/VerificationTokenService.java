package org.bootcamp.trashhunter.services.abstraction.tokens;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;


public interface VerificationTokenService<T extends VerificationToken> extends BaseTokenService<T>  {

    public void completeRegistration(VerificationToken token);

    public void sendToken(User registeredUser);
}
