package org.bootcamp.trashhunter.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Offer {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "name", unique = true)
    private String name;
}
