package com.ws.startupProject.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordUpdate(
        @Size(min = 8, max = 255)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Oluşturacağınız şifrede en az bir büyük harf bir sayı olması  gerekmektedir.")
        String password
) {
}
