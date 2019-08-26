package org.bootcamp.trashhunter.services.abstraction.tokens;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;


public interface VerificationTokenService extends BaseTokenService<VerificationToken>  {

    public void completeRegistration(VerificationToken token);

    public void sendToken(User registeredUser);

    public void sendTokenToResetPassword(User registeredUser);
}
