package com.ws.startupProject.categories.dto;

import com.ws.startupProject.categories.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriesCreate(
        @NotBlank(message = "Kategori İsmi Boş Olamaz")
        @Size(min = 1, max = 100)
        String name,
        String url,
        String image,
        String detail,
        @Size(max = 5)
        String language
) {
    public Categories toCategories() {
        Categories categories = new Categories();
        categories.setName(name);
        categories.setUrl(url);
        categories.setImage(image);
        categories.setDetail(detail);
        categories.setLanguage(language);
        return categories;
    }
}
