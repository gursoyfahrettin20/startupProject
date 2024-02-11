package com.ws.startupProject.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Credentials(@Email @NotBlank @NotNull String email, @NotBlank @NotNull String password) {
    
}
