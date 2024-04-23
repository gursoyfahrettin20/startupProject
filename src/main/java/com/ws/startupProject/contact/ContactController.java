package com.ws.startupProject.contact;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.contact.dto.ContactCreate;
import com.ws.startupProject.shared.GenericMessage;
import com.ws.startupProject.shared.Messages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    //    İletişim Bilgilerinin kaydedilmesi
    @PostMapping("/newContact/{id}")
    GenericMessage createContact(@PathVariable long id, @Valid @RequestBody ContactCreate contact, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator() && id == currentUser.getId()) {
            contactService.save(contact.toContact(), currentUser);
            message = new GenericMessage("website.contact.messages.createNewContact");
        }
        return message;
    }

    //    İletişim bilgilerinin listelenmesi
    @GetMapping("/contact")
    public List<Contact> getContact() {
        return contactService.getContact();
    }

    //    İletişim bilgilerinin silinmesi
    @PostMapping("/contact/{id}")
    GenericMessage deleteContact(@PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            contactService.deleteContact(id);
            message = new GenericMessage("Contact is Delete");
        }
        return message;
    }

    // iletişim bilgileinin güncellenmesi
    @PutMapping("/contact/{id}")
    GenericMessage updateContact(@PathVariable long id, @Valid @RequestBody Contact contact, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator() && id == currentUser.getId()) {
            contactService.update(contact);
            message = new GenericMessage("Contact is Update");
        }
        return message;
    }
}
