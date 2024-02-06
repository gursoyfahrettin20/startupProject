package com.ws.startupProject.user.dto;

import com.ws.startupProject.user.User;

import lombok.Data;

@Data
public class UserDTO {
    Long id;

    String username;

    String email;

    String image;

    Boolean isAdminstrator = false;

    String fullName;

    public UserDTO(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setEmail(user.getUsername());
        setIsAdminstrator(user.getIsAdminstrator());
        setFullName(user.getFirstName() + " " + user.getLastName());
    }

    public String getImage() {
        return image == null ? "default.png" : image;
    }

}
