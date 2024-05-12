package com.ws.startupProject.productToImages;

import com.ws.startupProject.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductToImagesRepository extends JpaRepository<ProductToImages, String> {
    List<ProductToImages> findByProductsId(String id);
}
