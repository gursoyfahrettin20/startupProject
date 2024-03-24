package com.ws.startupProject.auth;

import com.ws.startupProject.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
        var authResponse = authService.authenticate(creds);
        var cookie = ResponseCookie.from("fahrettin-token", authResponse.getToken().getToken()).path("/").httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/logout")
    ResponseEntity<GenericMessage> handleLogout(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @CookieValue(name = "fahrettin-token", required = false) String cookieValue) {
        var tokenWithPrefix = authorizationHeader;
        if (cookieValue != null) {
            tokenWithPrefix = "AnyPrefix " + cookieValue;
        }
        authService.logout(tokenWithPrefix);
        var cookie = ResponseCookie.from("fahrettin-token", "").path("/").maxAge(0).httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new GenericMessage("Logout success"));
    }
}
