package com.ws.startupProject.finishedWorks;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.finishedWorksToImage.FinishedWorksToImagesRepository;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionFinishedWorks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FinishedWorksService {

    @Autowired
    FinishedWorksRepository repository;

    @Autowired
    FinishedWorksToImagesRepository finishedWorksToImagesRepository;

    // Referans bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public FinishedWorks getFinishedWorks(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionFinishedWorks(id));
    }

    // Referansların kaydedilmesi
    public void saveFinishedWorks(FinishedWorks references, CurrentUser currentUser) {
        if (currentUser != null) {
            repository.save(references);
        } else {
            String message = Messages.getMessageForLocale("Referans Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Referansların listeleme alanı
    public Page<FinishedWorks> getFinishedWorks(Pageable page, CurrentUser currentUser) {
        if (currentUser != null) {
            if (currentUser.getIsAdministrator()) {
                return repository.findAll(page);
            }
        }
        return null;
    }

    // Web tarafında referansların listeleme alanı
    public Page<FinishedWorks> getWFinishedWorks(Pageable page) {
        return repository.findAll(page);
    }

    // Referansların silinmesi
    public void deleteFinishedWorks(String id) {
        FinishedWorks inDb = getFinishedWorks(id);
        if (inDb != null) {
            //  buraya referans resimleri altında bir veya birden fazla ürün varsa silinmeyecek kuralı eklenmesi gerekiyor.
            repository.delete(inDb);
        }
    }

    // Referansların güncellenmesi
    public Object updateFinishedWorks(FinishedWorks references) {
        FinishedWorks inDb = getFinishedWorks(references.id);
        if (inDb != null) {
            inDb.setName(references.name);
            inDb.setUrl(references.url);
            inDb.setDetail(references.detail);
            inDb.setFinishedWorksToImages(references.finishedWorksToImages);
        }
        return repository.save(inDb);
    }
}
