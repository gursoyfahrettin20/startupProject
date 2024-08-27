package com.ws.startupProject.finishedWorksToImage;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.finishedWorksToImage.dto.FinishedWorksToImageCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FinishedWorksToImageController {

    @Autowired
    FinishedWorksToImageService service;

    // Referans resimlerinin kaydedilmesi
    @PostMapping("/newFinishedWorksToImage")
    GenericMessage createFinishedWorksToImages(@Valid @RequestBody FinishedWorksToImageCreate[] finishedWorksToImageCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Siteye Giriş Yapmadınız");
        if (currentUser.getIsAdministrator()) {
            for (FinishedWorksToImageCreate toImageCreate : finishedWorksToImageCreate) {
                service.saveFinishedWorksToImage(toImageCreate.toFinishedWorksToImages(), currentUser);
            }
            message = new GenericMessage("FinishedWorks is created");
        }
        return message;
    }

    // Referans resimlerinin listelenmesi
    @GetMapping("/finishedWorksToImage/{id}")
    public List<FinishedWorksToImages> getFinishedWorks(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getIsAdministrator()) {
            return service.getFinishedWorksImageList(id);
        }
        return null;
    }

    // Web tarafında Referans resimlerinin listelenmesi
    @GetMapping("/wFinishedWorksToImage/{id}")
    public List<FinishedWorksToImages> getFinishedWorks(@PathVariable String id) {
        return service.getFinishedWorksImageList(id);
    }

    // Referans resimlerinin silinmesi
    @DeleteMapping("/finishedWorksToImage/{id}")
    GenericMessage deleteFinishedWorksToImages(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi değilsiniz");
        if (currentUser.getIsAdministrator()) {
            service.deleteFinishedWorksToImage(id);
        }
        message = new GenericMessage("Reference to image is delete");
        return message;
    }

    // Referans resimlerinin güncellenmesi
    @PutMapping("/finishedWorksToImage")
    GenericMessage updateFinishedWorksToImage(@Valid @RequestBody FinishedWorksToImageCreate[] finishedWorksToImageCreates, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi değilsiniz");
        if (currentUser.getIsAdministrator()) {
            for (FinishedWorksToImageCreate toImageCreate : finishedWorksToImageCreates) {
                service.updateFinishedWorksToImage(toImageCreate.toFinishedWorksToImages());
            }
            message = new GenericMessage("Reference to image is update");
        }
        return message;
    }
}
