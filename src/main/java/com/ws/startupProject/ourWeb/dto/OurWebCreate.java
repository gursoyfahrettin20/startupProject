package com.ws.startupProject.ourWeb.dto;

import com.ws.startupProject.ourWeb.OurWeb;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OurWebCreate(
//        @NotBlank(message = "Boş Olamaz")
//        @Size(min = 1, max = 100)
        String name,
        @NotBlank(message = "Boş Olamaz")
        @Size(min = 1)
        String detail,
        String image,

        @Size(max = 5)
        String language
) {
    public OurWeb toOurWeb() {
        OurWeb ourWeb = new OurWeb();
        ourWeb.setName(name);
        ourWeb.setDetail(detail);
        ourWeb.setImage(image);
        ourWeb.setLanguage(language);
        return ourWeb;
    }
}
