package com.ws.startupProject.slider.dto;

import com.ws.startupProject.slider.Slider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SliderCreate(
        @NotBlank(message = "Slider İsmi Boş Olamaz")
        @Size(min = 1, max = 150)
        String name,

        String image,

        @Size(max = 1200)
        String detail,

        @Size(max = 300)
        String shortDetail,


        @Size(max = 150)
        String link
) {

        public Slider toSlider() {
                Slider slider = new Slider();
                slider.setName(name);
                slider.setLink(link);
                slider.setImage(image);
                slider.setShortDetail(shortDetail);
                slider.setDetail(detail);
                return slider;
        }
}
