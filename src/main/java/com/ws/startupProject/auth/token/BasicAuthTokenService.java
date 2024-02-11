package com.ws.startupProject.auth.token;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.ws.startupProject.auth.dto.Credentials;
import com.ws.startupProject.user.User;

@Service
public class BasicAuthTokenService implements TokenService {

    @Override
    public Token CreateToken(User user, Credentials credentials) {
        String emailAndPassword = credentials.email() + ":" + credentials.password();
        String token = Base64.getEncoder().encodeToString(emailAndPassword.getBytes());
        return new Token ("Basic", token) ;
    }

    @Override
    public User VerifyToken(String AuthorizationHeader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'VerifyToken'");
    }

}
