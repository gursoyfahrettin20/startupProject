package com.ws.startupProject.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ws.startupProject.auth.Exception.AuthenticationExcepion;
import com.ws.startupProject.auth.dto.AuthResponse;
import com.ws.startupProject.auth.dto.Credentials;
import com.ws.startupProject.auth.token.Token;
import com.ws.startupProject.auth.token.TokenService;
import com.ws.startupProject.user.User;
import com.ws.startupProject.user.UserService;
import com.ws.startupProject.user.dto.UserDTO;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(Credentials creds) {
        User inDb = userService.finByEmail(creds.email());
        if (inDb == null) {
            throw new AuthenticationExcepion();
        }
        if (!passwordEncoder.matches(creds.password(), inDb.getPassword())) {
            throw new AuthenticationExcepion();
        }
        Token token = tokenService.CreateToken(inDb, creds);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(new UserDTO(inDb));
        return authResponse;
    }
}
