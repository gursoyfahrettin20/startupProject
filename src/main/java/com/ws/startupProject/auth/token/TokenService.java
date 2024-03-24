package com.ws.startupProject.auth.token;

import com.ws.startupProject.auth.dto.Credentials;
import com.ws.startupProject.user.User;

public interface TokenService {

    public Token createToken(User user, Credentials credentials);

    public User verifyToken(String AuthorizationHeader);

    public void logout(String authorizationHeader);

}
