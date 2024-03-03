package com.ws.startupProject.auth.token;

import java.util.Base64;

import com.ws.startupProject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ws.startupProject.auth.dto.Credentials;
import com.ws.startupProject.user.User;

@Service
public class BasicAuthTokenService implements TokenService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Token CreateToken(User user, Credentials credentials) {
        String emailAndPassword = credentials.email() + ":" + credentials.password();
        String token = Base64.getEncoder().encodeToString(emailAndPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User VerifyToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }
        var base64Encoded = authorizationHeader.split("Basic ")[1];
        var decodedValue = new String(Base64.getDecoder().decode(base64Encoded));

        var credentials = decodedValue.split(":");
        var email = credentials[0];
        var password = credentials[1];

        User inDb = userService.finByEmail(email);
        if (inDb == null) return null;
        if (!passwordEncoder.matches(password, inDb.getPassword())) return null;

        return inDb;
    }

}
