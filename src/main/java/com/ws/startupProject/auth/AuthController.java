package com.ws.startupProject.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.ws.startupProject.auth.dto.AuthResponse;
import com.ws.startupProject.auth.dto.Credentials;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/auth")
    AuthResponse handleAuthantication(@Valid @RequestBody Credentials credentials) {
       return authService.authenticate(credentials);
    }


}
