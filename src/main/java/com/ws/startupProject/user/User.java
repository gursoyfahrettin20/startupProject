package com.ws.startupProject.user;

import com.ws.startupProject.auth.token.Token;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue
    Long id;

    String username;

    String email;

    String password;

    Boolean active = false;

    String activationToken;

    String passwordResetToken;

    @Column(columnDefinition = "Text")
    String image;

    Boolean isAdministrator = false;

    String firstName;

    String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    List<Token> tokens;

}
