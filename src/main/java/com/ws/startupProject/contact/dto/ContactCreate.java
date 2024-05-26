package com.ws.startupProject.contact.dto;

import com.ws.startupProject.contact.Contact;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactCreate(
        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 100)
        String branchName,
        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 255)
        String address,

        @Size(min = 0, max = 20)
        String mobilNumber,

        @Size(min = 0, max = 20)
        String branchNumber,

        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 50)
        String mail,

        @Lob
        String maps,

        @Size(max = 5)
        String language

) {
    public Contact toContact() {
        Contact contact = new Contact();
        contact.setBranchName(branchName);
        contact.setAddress(address);
        contact.setMobilNumber(mobilNumber);
        contact.setBranchNumber(branchNumber);
        contact.setMail(mail);
        contact.setMaps(maps);
        contact.setLanguage(language);
        return contact;
    }
}
