package com.ws.startupProject.news.dto;

import com.ws.startupProject.news.News;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewsCreate(
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
    public News toNews() {
        News news = new News();
        news.setName(name);
        news.setLink(link);
        news.setImage(image);
        news.setShortDetail(shortDetail);
        news.setDetail(detail);
        return news;
    }
}
