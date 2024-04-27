package com.ws.startupProject.contact.dto;

import com.ws.startupProject.contact.Contact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactCreate(
        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 100)
        String branchName,
        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 255)
        String address,

        @Size(min = 0, max = 13)
        String mobilNumber,

        @Size(min = 1, max = 13)
        String branchNumber,

        @NotBlank(message = "{website.contact.messages.notnull}")
        @Size(min = 1, max = 50)
        String mail,


        @Size(max = 500)
        String maps

) {
    public Contact toContact() {
        Contact contact = new Contact();
        contact.setBranchName(branchName);
        contact.setAddress(address);
        contact.setMobilNumber(mobilNumber);
        contact.setBranchNumber(branchNumber);
        contact.setMail(mail);
        contact.setMaps(maps);
        return contact;
    }
}
