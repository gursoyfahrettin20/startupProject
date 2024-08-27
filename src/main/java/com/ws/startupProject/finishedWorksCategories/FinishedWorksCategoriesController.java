package com.ws.startupProject.finishedWorksCategories;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.finishedWorksCategories.dto.FinishedWorksCategoriesCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FinishedWorksCategoriesController {
    @Autowired
    FinishedWorksCategoriesService finishedWorksCategoriesService;

    // Kategorileri Bilgilerinin kaydedilmesi
    @PostMapping("/newCategoriesFinishedWorks")
    GenericMessage createCategories(@Valid @RequestBody FinishedWorksCategoriesCreate finishedWorksCategoriesCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
                    finishedWorksCategoriesService.saveCategories(finishedWorksCategoriesCreate.toFinishedWorksCategories(), currentUser);
            message = new GenericMessage("Categories is created");
        }
        return message;
    }

    // Kategorileri bilgilerinin listelenmesi
    @GetMapping("/categoriesFinishedWorks")
    public List<FinishedWorksCategories> getCategories() {
        return finishedWorksCategoriesService.getCategories();
    }

    // Kategorileri bilgilerinin silinmesi
    @DeleteMapping("/categoriesFinishedWorks/{id}")
    GenericMessage deleteCategories(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            finishedWorksCategoriesService.deleteCategories(id);
            message = new GenericMessage("Categories is Delete");
        }
        return message;
    }

    // Kategorileri bilgilerinin güncellenmesi
    @PutMapping("/categoriesFinishedWorks")
    GenericMessage updateCategories(@Valid @RequestBody FinishedWorksCategories finishedWorksCategories, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            finishedWorksCategoriesService.updateCategories(finishedWorksCategories);
            message = new GenericMessage("Categories is Update");
        }
        return message;
    }
}
