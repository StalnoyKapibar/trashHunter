package org.bootcamp.trashhunter.models;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Taker extends User {

    public Taker() {
    }

    public Taker(String email, String name, String password, LocalDate registrationDate) {
        super(email, name, password, registrationDate);
    }
}
