package com.ws.startupProject.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

    String image;

    Boolean isAdminstrator = false;

    String firstName;

    String lastName;

}
