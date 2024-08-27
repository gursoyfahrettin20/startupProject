package com.ws.startupProject.finishedWorksToImage;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.configuration.WebSiteConfigurationProperties;
import com.ws.startupProject.file.FileService;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionFinishedWorksToImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedWorksToImageService {
    @Autowired
    FinishedWorksToImagesRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;

    // Referansa ait resim bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public FinishedWorksToImages getFinishedWorksToImage(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionFinishedWorksToImage(id));
    }

    // Referans fotoğraflarının kaydedilmesi
    public void saveFinishedWorksToImage(FinishedWorksToImages finishedWorksToImages, CurrentUser currentUser) {
        if (null != currentUser) {
            if (null != finishedWorksToImages.getImage()) {
                String fileName = fileService.saveBase64StringAsFile(finishedWorksToImages.getImage(), properties.getStorage().getFinishedWorks(), finishedWorksToImages.finishedWorks.getName());
                finishedWorksToImages.setImage(fileName);
            }
            repository.save(finishedWorksToImages);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Referans fotoğraflarının listeleme alanı
    public List<FinishedWorksToImages> getFinishedWorksImageList(String id) {
        return repository.findByFinishedWorksId(id);
    }

    // Referans fotoğraflarının silinmesi
    public void deleteFinishedWorksToImage(String id) {
        FinishedWorksToImages inDb = getFinishedWorksToImage(id);
        if (null != inDb) {
            fileService.deleteImageFolder(properties.getStorage().getFinishedWorks(), inDb.getImage());
            repository.delete(inDb);
        }
    }

    // Referans fotoğraflarının güncellenmesi
    public Object updateFinishedWorksToImage(FinishedWorksToImages finishedWorksToImages) {
        FinishedWorksToImages inDb = getFinishedWorksToImage(finishedWorksToImages.id);
        if (null != inDb) {
            inDb.setImage(finishedWorksToImages.image);
        }
        return repository.save(inDb);
    }

}
