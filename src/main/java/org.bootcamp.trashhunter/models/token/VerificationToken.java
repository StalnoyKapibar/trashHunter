package org.bootcamp.trashhunter.models.token;

import org.bootcamp.trashhunter.models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class VerificationToken extends BaseToken {

    public VerificationToken() {
    }

    public VerificationToken(String token, User user, Date expiryDate) {
        super(token, user, expiryDate);
    }
}
