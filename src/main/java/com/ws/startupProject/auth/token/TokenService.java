package com.ws.startupProject.auth.token;

import com.ws.startupProject.auth.dto.Credentials;
import com.ws.startupProject.user.User;

public interface TokenService {
    
    public Token CreateToken(User user, Credentials credentials);
    public User VerifyToken(String AuthorizationHeader);
}
