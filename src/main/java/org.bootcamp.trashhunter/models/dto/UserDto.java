package org.bootcamp.trashhunter.models.dto;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;

public class UserDto {

    private long id;

    private String email;

    private String name;

    private String password;

    private LocalDate registrationDate;

    private String aboutUser;

    private String city;

    private byte[] pic;

    @Column
    private boolean enabled;

}
