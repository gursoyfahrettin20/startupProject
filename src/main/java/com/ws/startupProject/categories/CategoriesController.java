package com.ws.startupProject.categories;

import com.ws.startupProject.categories.dto.CategoriesCreate;
import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    // Kategorileri Bilgilerinin kaydedilmesi
    @PostMapping("/newCategories")
    GenericMessage createCategories(@Valid @RequestBody CategoriesCreate categoriesCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Siteye Giriş yapmadınız.");
        if (currentUser.getIsAdministrator()) {
            categoriesService.saveCategories(categoriesCreate.toCategories(), currentUser);
            message = new GenericMessage("Categories is created");
        }
        return message;
    }

    //    Kategorileri bilgilerinin listelenmesi
    @GetMapping("/categories")
    public List<Categories> getCategories() {
        return categoriesService.getCategories();
    }

    //    Kategorileri bilgilerinin silinmesi
    @DeleteMapping("/categories/{id}")
    GenericMessage deleteCategories(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            categoriesService.deleteCategories(id);
            message = new GenericMessage("Categories is Delete");
        }
        return message;
    }

    // iletişim bilgileinin güncellenmesi
    @PutMapping("/categories")
    GenericMessage updateCategories(@Valid @RequestBody Categories categories, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            categoriesService.updateCategories(categories);
            message = new GenericMessage("Categories is Update");
        }
        return message;
    }
}
