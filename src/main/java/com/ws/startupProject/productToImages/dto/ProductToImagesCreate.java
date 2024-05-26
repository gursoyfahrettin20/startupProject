package com.ws.startupProject.productToImages.dto;

import com.ws.startupProject.productToImages.ProductToImages;
import com.ws.startupProject.products.Products;
import jakarta.validation.constraints.Size;

public record ProductToImagesCreate(
        String image,

        Products products,

        @Size(max = 5)
        String language
) {
    public ProductToImages toProductToImages() {
        ProductToImages productToImages = new ProductToImages();
        productToImages.setImage(image);
        productToImages.setProducts(products);
        productToImages.setLanguage(language);
        return productToImages;
    }
}
