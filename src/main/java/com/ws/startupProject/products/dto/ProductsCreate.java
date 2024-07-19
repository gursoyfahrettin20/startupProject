package com.ws.startupProject.products.dto;

import com.ws.startupProject.categories.Categories;
import com.ws.startupProject.productToImages.ProductToImages;
import com.ws.startupProject.products.Products;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public record ProductsCreate(
        @NotBlank(message = "Ürün İsmi Boş Olamaz")
        @Size(min = 1, max = 150)
        String name,
        String url,

        String detail,
        Categories categories,
        ProductToImages productToImages,
        @Size(max = 5)
        String language
) {
    public Products toProducts() {
        Products products = new Products();
        products.setName(name);
        products.setUrl(url);
        products.setDetail(detail);
        products.setCategories(categories);
        products.setLanguage(language);
        products.setProductToImages(Collections.singletonList(productToImages));
        return products;
    }
}
