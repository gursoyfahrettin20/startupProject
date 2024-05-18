package com.ws.startupProject.ourWeb;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.configuration.WebSiteConfigurationProperties;
import com.ws.startupProject.file.FileService;
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

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;

    // OurWeb Bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public OurWeb getOurWeb(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionContact(id));
    }

    //  OurWeb kaydedilmesi
    public void save(OurWeb ourWeb, CurrentUser currentUser) {
        if (currentUser != null) {
            if (ourWeb.getImage() != null) {
                String filename = fileService.saveBase64StringAsFile(ourWeb.getImage(), properties.getStorage().getPages(), ourWeb.getName());
                ourWeb.setImage(filename);
            }
            repository.save(ourWeb);
        } else {
            String message = Messages.getMessageForLocale("website.contact.messages.notFoundUser", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    //  OurWeb  listeleme alanı
    public List<OurWeb> getOurWeb() {
        return repository.findAll();
    }

    //  OurWeb  silinmesi
    public void deleteOurWeb(long id) {
        OurWeb inDb = getOurWeb(id);
        if (inDb != null) {
            repository.delete(inDb);
        }
    }

    // OurWeb güncellenmesi
    public Object update(OurWeb ourWeb) {
        OurWeb inDb = getOurWeb(ourWeb.id);
        if (inDb != null) {
            if (ourWeb.getImage() != null) {
                fileService.deleteImageFolder(properties.getStorage().getPages(), inDb.getImage());
                String filename = fileService.saveBase64StringAsFile(ourWeb.getImage(), properties.getStorage().getPages(), ourWeb.getName());
                ourWeb.setImage(filename);
            }
            inDb.setDetail(ourWeb.detail);
            inDb.setImage(ourWeb.image);
        }
        return repository.save(inDb);
    }
}
