package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;

public interface MailServiceI {
    void sendMessage(User user, VerificationToken token);
}
