package com.ws.startupProject.contact;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository repository;

    // İlitişim Bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public Contact getContact(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionContact(id));
    }

    //    kaydedilmesi
    public void save(Contact contact, CurrentUser currentUser) {
        if (currentUser != null) {
            repository.save(contact);
        } else {
            String message = Messages.getMessageForLocale("website.contact.messages.notFoundUser", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    //    listeleme alanı
    public List<Contact> getContact() {
        return repository.findAll();
    }

    //    silinmesi
    public void deleteContact(long id) {
        Contact inDb = getContact(id);
        if (inDb != null) {
            repository.delete(inDb);
        }
    }

    // güncellenmesi
    public Object update(Contact contact) {
        Contact inDb = getContact(contact.id);
        if (inDb != null) {
            inDb.setBranchName(contact.branchName);
            inDb.setAddress(contact.address);
            inDb.setMobilNumber(contact.mobilNumber);
            inDb.setBranchNumber(contact.branchNumber);
            inDb.setMail(contact.mail);
        }
        return repository.save(inDb);
    }
}
