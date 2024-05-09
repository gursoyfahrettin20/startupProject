package com.ws.startupProject.productToImages;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.productToImages.dto.ProductToImagesCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductToImageController {

    @Autowired
    ProductToImageService service;

    // Ürünlerin resimlerinin kaydedilmesi

    @PostMapping("/newProductToImage")
    GenericMessage createProductToImages(@Valid @RequestBody ProductToImagesCreate[] productToImagesCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Siteye Giriş yapmadınız.");
        if (currentUser.getIsAdministrator()) {
            for (ProductToImagesCreate toImagesCreate : productToImagesCreate) {
                service.saveProductToImage(toImagesCreate.toProductToImages(), currentUser);
            }
            message = new GenericMessage("Product is created");
        }
        return message;
    }

    // Ürün bilgilerinin listelenmesi
    @GetMapping("/productToImage")
    public List<ProductToImages> getProduct() {
        return service.getProducts();
    }


    // Ürün bilgilerinin silinmesi
    @DeleteMapping("/productToImage/{id}")
    GenericMessage deleteProductToImages(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.deleteProductToImage(id);
            message = new GenericMessage("Product to image is Delete");
        }
        return message;
    }


    // Ürün bilgileinin güncellenmesi
    @PutMapping("/productToImage")
    GenericMessage updateProductToImages(@Valid @RequestBody ProductToImages productToImages, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.updateProductToImages(productToImages);
            message = new GenericMessage("Product to image is Update");
        }
        return message;
    }

}
