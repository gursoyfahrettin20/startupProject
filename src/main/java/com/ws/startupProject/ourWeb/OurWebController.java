package com.ws.startupProject.ourWeb;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.ourWeb.dto.OurWebCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OurWebController {

    @Autowired
    OurWebService ourWebService;

    //    Hakkımızda/Vizyonumuz/Misyonumuz Bilgilerinin kaydedilmesi
    @PostMapping("/newOurWeb/{id}")
    GenericMessage createOurWeb(@PathVariable long id, @RequestBody OurWebCreate ourWebCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator() && id == currentUser.getId()) {
            ourWebService.save(ourWebCreate.toOurWeb(), currentUser);
            message = new GenericMessage("website.contact.messages.createNewOurWeb");
        }
        return message;
    }

    //    Hakkımızda/Vizyonumuz/Misyonumuz bilgilerinin listelenmesi
    @GetMapping("/ourWeb")
    public List<OurWeb> getOurWeb() {
        return ourWebService.getOurWeb();
    }

    //    Hakkımızda/Vizyonumuz/Misyonumuz bilgilerinin silinmesi
    @PostMapping("/ourWeb/{id}")
    GenericMessage deleteOurWeb(@PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            ourWebService.deleteOurWeb(id);
            message = new GenericMessage("Our is Delete");
        }
        return message;
    }

    // Hakkımızda/Vizyonumuz/Misyonumuz bilgileinin güncellenmesi
    @PutMapping("/ourWeb/{id}")
    GenericMessage updateOurWeb(@PathVariable long id, @RequestBody OurWeb ourWeb, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator() && id == currentUser.getId()) {
            ourWebService.update(ourWeb);
            message = new GenericMessage("Our is Update");
        }
        return message;
    }
}
