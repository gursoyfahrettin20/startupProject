package com.ws.startupProject.finishedWorksCategories.dto;

import com.ws.startupProject.finishedWorksCategories.FinishedWorksCategories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FinishedWorksCategoriesCreate(
        @NotBlank(message = "Kategori İsmi Boş Olamaz")
        @Size(min = 1, max = 100)
        String name,
        String url,
        String image,
        String detail,
        @Size(max = 5)
        String language
) {
    public FinishedWorksCategories toFinishedWorksCategories() {
        FinishedWorksCategories finishedWorksCategories = new FinishedWorksCategories();
        finishedWorksCategories.setName(name);
        finishedWorksCategories.setUrl(url);
        finishedWorksCategories.setImage(image);
        finishedWorksCategories.setDetail(detail);
        finishedWorksCategories.setLanguage(language);
        return finishedWorksCategories;
    }
}
