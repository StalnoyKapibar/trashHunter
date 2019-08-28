package org.bootcamp.trashhunter.models;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Sender extends User {

    public Sender() {}

    public Sender(String email, String name, String password, LocalDate registrationDate, String city, byte [] pic) {
        super(email, name, password, registrationDate, city, pic);
    }
}
