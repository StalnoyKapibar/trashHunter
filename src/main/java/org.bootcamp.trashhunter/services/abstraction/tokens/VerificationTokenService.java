package org.bootcamp.trashhunter.services.abstraction.tokens;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;


public interface VerificationTokenService extends BaseTokenService<VerificationToken>  {

   void completeRegistration(VerificationToken token);

   void sendToken(User registeredUser);
}
