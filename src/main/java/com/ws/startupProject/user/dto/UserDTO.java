package com.ws.startupProject.user.dto;

import com.ws.startupProject.user.User;

import lombok.Data;

@Data
public class UserDTO {
    Long id;

    String username;

    String email;

    String image;

    Boolean isAdministrator = false;

    String fullName;

    public UserDTO(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setImage(user.getImage());
        setEmail(user.getEmail());
        setIsAdministrator(user.getIsAdministrator());
        setFullName(user.getFirstName() + " " + user.getLastName());
    }

    public String getImage() {
        return image == null ? "default.png" : image;
    }

}
