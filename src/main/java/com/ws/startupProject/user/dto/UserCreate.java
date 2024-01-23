package com.ws.startupProject.user.dto;

import org.hibernate.annotations.NotFound;

import com.ws.startupProject.user.User;
import com.ws.startupProject.user.validation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(
        @NotBlank(message = "{startupProject.constraint.notNull}") // java tarafında dil(tr-en) dosyası kullanım irneği içindir.
        @NotNull 
        @NotFound 
        @NotEmpty 
        @Size(min = 4, max = 255) 
        String username,

        @NotBlank 
        @Email 
        @UniqueEmail 
        String email,

        @NotBlank 
        @Size(min = 8, max = 255) 
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Oluşturacağınız şifrede en az bir büyük harf bir sayı olması  gerekmektedir.") 
        String password) {
    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
