package com.ws.startupProject.categories;

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
public class CategoriesService {

    @Autowired
    CategoriesRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;


    // İlitişim Bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public Categories getCategory(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionCategories(id));
    }

    // Kategorilerin kaydedilmesi
    public void saveCategories(Categories categories, CurrentUser currentUser) {
        if (currentUser != null) {
            if (categories.getImage() != null) {
                String filename = fileService.saveBase64StringAsFileCategories(categories.getImage(), properties.getStorage().getCategory(), categories.getName());
                categories.setImage(filename);
            }
            repository.save(categories);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Kategorilerin listeleme alanı
    public List<Categories> getCategories() {
        return repository.findAll();
    }

    // Kategorilerin silinmesi
    public void deleteCategories(String id) {
        Categories inDb = getCategory(id);
        if (inDb != null) {
            fileService.deleteCategoryImage(properties.getStorage().getCategory(), inDb.getImage());
            repository.delete(inDb);
        }
    }

    // Kategorilerin güncellenmesi
    public Object updateCategories(Categories categories) {
        Categories inDb = getCategory(categories.id);
        if (inDb != null) {
            if (categories.getImage() != null) {
                // Yeni resim ekleme bloğu
                String filename = fileService.saveBase64StringAsFileCategories(categories.getImage(), properties.getStorage().getCategory(), inDb.getName());
                // Kategori resimini güncellediğinde eski resmi silme bloğu başlangıcı
                fileService.deleteCategoryImage(properties.getStorage().getCategory(), inDb.getImage());
                inDb.setImage(filename);
            }
            inDb.setName(categories.name);
            inDb.setDetail(categories.detail);
        }
        return repository.save(inDb);
    }
}
