package com.ws.startupProject.auth.dto;

import com.ws.startupProject.auth.token.Token;
import com.ws.startupProject.user.dto.UserDTO;

import lombok.Data;

@Data
public class AuthResponse {
    UserDTO user;
    Token token;
}
