package com.ws.startupProject.productToImages.dto;

import com.ws.startupProject.productToImages.ProductToImages;
import com.ws.startupProject.products.Products;

public record ProductToImagesCreate(
        String image,

        Products products
) {
    public ProductToImages toProductToImages() {
        ProductToImages productToImages = new ProductToImages();
        productToImages.setImage(image);
        productToImages.setProducts(products);
        return productToImages;
    }
}
