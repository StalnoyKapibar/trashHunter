package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.token.VerificationToken;

import java.util.Map;

public interface MailService {

    void sendMessage(User user, VerificationToken token);

    void sendMessage(User user, String message, String subject);

    void sendMessageWithModel(String emailTo, String subject, Map<String, Object> modelMessage);
}
