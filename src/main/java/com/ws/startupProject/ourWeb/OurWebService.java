package com.ws.startupProject.ourWeb;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OurWebService {

    @Autowired
    OurWebRepository repository;

    // İlitişim Bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public OurWeb getOurWeb(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionContact(id));
    }

    //    kaydedilmesi
    public void save(OurWeb ourWeb, CurrentUser currentUser) {
        if (currentUser != null) {
            repository.save(ourWeb);
        } else {
            String message = Messages.getMessageForLocale("website.contact.messages.notFoundUser", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    //    listeleme alanı
    public List<OurWeb> getOurWeb() {
        return repository.findAll();
    }

    //    silinmesi
    public void deleteOurWeb(long id) {
        OurWeb inDb = getOurWeb(id);
        if (inDb != null) {
            repository.delete(inDb);
        }
    }

    // güncellenmesi
    public Object update(OurWeb ourWeb) {
        OurWeb inDb = getOurWeb(ourWeb.id);
        if (inDb != null) {
            inDb.setDetail(ourWeb.detail);
        }
        return repository.save(inDb);
    }
}
