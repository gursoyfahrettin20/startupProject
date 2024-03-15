package com.ws.startupProject.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User {

    @Id
    @GeneratedValue
    Long id;

    String username;

    String email;

    String password;

    Boolean active = false;

    String activationToken;

    @Lob
    String image;

    Boolean isAdministrator = false;

    String firstName;

    String lastName;

}
