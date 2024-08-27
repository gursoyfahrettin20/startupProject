package com.ws.startupProject.finishedWorksCategories;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.configuration.WebSiteConfigurationProperties;
import com.ws.startupProject.file.FileService;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedWorksCategoriesService {
    @Autowired
    FinishedWorksCategoriesRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;

    // id 'ye ait bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public FinishedWorksCategories getCategories(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionCategories(id));
    }

    // Kategorilerin kaydedilmesi
    public void saveCategories(FinishedWorksCategories finishedWorksCategories, CurrentUser currentUser) {
        if (null != currentUser) {
            if (null != finishedWorksCategories.getImage()) {
                String fileName = fileService.saveBase64StringAsFile(finishedWorksCategories.getImage(), properties.getStorage().getFinishedWorks(), finishedWorksCategories.getName());
                finishedWorksCategories.setImage(fileName);
            }
            repository.save(finishedWorksCategories);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Kategorilerin listeleme alanı
    public List<FinishedWorksCategories> getCategories() {
        return repository.findAll();
    }

    // Kategorilerin silinmesi
    public void deleteCategories(String id) {
        FinishedWorksCategories inDb = getCategories(id);
        if (null != inDb) {
            fileService.deleteImageFolder(properties.getStorage().getCategory(), inDb.getImage());
            repository.delete(inDb);
        }
    }

    // Kategorilerin güncellenmesi
    public Object updateCategories(FinishedWorksCategories finishedWorksCategories) {
        FinishedWorksCategories inDb = getCategories(finishedWorksCategories.id);
        if (null != inDb) {
            if (null != finishedWorksCategories.getImage()) {
                // Yeni resim ekleme bloğu
                String fileName = fileService.saveBase64StringAsFile(finishedWorksCategories.getImage(), properties.getStorage().getFinishedWorks(), inDb.getName());
                // Kategori resimini güncellediğinde eski resmi silme bloğu başlangıcı
                fileService.deleteImageFolder(properties.getStorage().getFinishedWorks(), inDb.getImage());
                inDb.setImage(fileName);
            }
            inDb.setName(finishedWorksCategories.name);
            inDb.setUrl(finishedWorksCategories.url);
            inDb.setDetail(finishedWorksCategories.detail);
        }
        return repository.save(inDb);
    }
}
