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

        @Size(max = 1200)
        String detail,

        Categories categories,

        ProductToImages productToImages
) {
    public Products toProducts() {
        Products products = new Products();
        products.setName(name);
        products.setDetail(detail);
        products.setCategories(categories);
        products.setProductToImages(Collections.singletonList(productToImages));
        return products;
    }
}
