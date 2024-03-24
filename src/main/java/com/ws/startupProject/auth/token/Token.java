package com.ws.startupProject.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ws.startupProject.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Token {
    @Id
    String token;

    @Transient
    String prefix = "fahrettin-token";

    @JsonIgnore
    @ManyToOne
    User user;

    public Token(String prefix, String token) {
        this.prefix = prefix;
        this.token = token;
    }

    public Token(){}
}

