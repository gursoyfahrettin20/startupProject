package com.ws.startupProject.user.dto;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
    long getId();

    String getUsername();

    String getEmail();

    @Value("#{target.image != null ? target.image : 'default.png'}")
    String getImage();

    Boolean getIsAdministrator();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}
