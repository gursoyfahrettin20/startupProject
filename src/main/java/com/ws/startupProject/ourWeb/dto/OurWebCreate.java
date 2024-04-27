package com.ws.startupProject.ourWeb.dto;

import com.ws.startupProject.ourWeb.OurWeb;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OurWebCreate(
//        @NotBlank(message = "{website.contact.messages.notnull}")
//        @Size(min = 1, max = 100)
        String name,
        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 10000)
        String detail,
        String image
) {
    public OurWeb toOurWeb() {
        OurWeb ourWeb = new OurWeb();
        ourWeb.setName(name);
        ourWeb.setDetail(detail);
        ourWeb.setImage(image);
        return ourWeb;
    }
}
