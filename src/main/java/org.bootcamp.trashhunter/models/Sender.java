package org.bootcamp.trashhunter.models;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Sender extends User {

    public Sender() {
    }

    public Sender(String email, String name, String password, LocalDate registrationDate) {
        super(email, name, password, registrationDate);
    }
}
