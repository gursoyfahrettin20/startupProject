package com.ws.startupProject.products;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.products.dto.ProductDto;
import com.ws.startupProject.products.dto.ProductsCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductsController {

    @Autowired
    ProductsService service;

    // Ürünlerin Bilgilerinin kaydedilmesi
    @PostMapping("/newProducts")
    GenericMessage createProducts(@Valid @RequestBody ProductsCreate productsCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Siteye Giriş yapmadınız.");
        if (currentUser.getIsAdministrator()) {
            service.saveProduct(productsCreate.toProducts(), currentUser);
            message = new GenericMessage("Product is created");
        }
        return message;
    }

    // Ürün bilgilerinin listelenmesi
    @GetMapping("/products")
    public Page<ProductDto> getProducts(Pageable page, @AuthenticationPrincipal CurrentUser currentUser) {
        return service.getProducts(page, currentUser).map(ProductDto::new);
    }


    // Ürün bilgilerinin silinmesi
    @DeleteMapping("/products/{id}")
    GenericMessage deleteProducts(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.deleteProducts(id);
            message = new GenericMessage("Products is Delete");
        }
        return message;
    }


    // Ürün bilgileinin güncellenmesi
    @PutMapping("/products")
    GenericMessage updateProducts(@Valid @RequestBody Products products, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.updateProducts(products);
            message = new GenericMessage("Products is Update");
        }
        return message;
    }

}
