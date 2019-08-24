package org.bootcamp.trashhunter.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column
    private String aboutUser;

    @Lob
    @Column(name="pic")
    private byte[] pic;

    private boolean enabled;

    public User() {
        this.enabled = false;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public User(String email, String name, String password, LocalDate registrationDate) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.registrationDate = registrationDate;
    }

    public String getAboutUser() { return aboutUser; }

    public void setAboutUser(String aboutUser) { this.aboutUser = aboutUser; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
