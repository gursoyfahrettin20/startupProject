package com.ws.startupProject.finishedWorks;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.finishedWorks.dto.FinishedWorksDto;
import com.ws.startupProject.finishedWorks.dto.FinishedWorksCreate;
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
public class FinishedWorksController {

    @Autowired
    FinishedWorksService service;

    // Referans Bilgilerinin kaydedilmesi
    @PostMapping("/newFinishedWorks")
    GenericMessage createFinishedWorks(@Valid @RequestBody FinishedWorksCreate finishedWorksCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.saveFinishedWorks(finishedWorksCreate.toFinishedWorks(), currentUser);
            message = new GenericMessage("Reference is created");
        }
        return message;
    }

    // Referans bilgilerinin listelenmesi
    @GetMapping("/finishedWorks")
    public Page<FinishedWorksDto> getFinishedWorks(Pageable page, @AuthenticationPrincipal CurrentUser currentUser) {
        return service.getFinishedWorks(page, currentUser).map(FinishedWorksDto::new);
    }

    // Web tarafında Referans bilgilerinin listelenmesi
    @GetMapping("/wFinishedWorks")
    public Page<FinishedWorksDto> getProducts(Pageable page) {
        return service.getWFinishedWorks(page).map(FinishedWorksDto::new);
    }

    // Referans bilgilerinin silinmesi
    @DeleteMapping("/finishedWorks/{id}")
    GenericMessage deleteFinishedWorks(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.deleteFinishedWorks(id);
            message = new GenericMessage("FinishedWorks is Delete");
        }
        return message;
    }


    // Referans bilgileinin güncellenmesi
    @PutMapping("/finishedWorks")
    GenericMessage updateFinishedWorks(@Valid @RequestBody FinishedWorks finishedWorks, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.updateFinishedWorks(finishedWorks);
            message = new GenericMessage("FinishedWorks is Update");
        }
        return message;
    }

}
