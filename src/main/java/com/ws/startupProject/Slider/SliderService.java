package com.ws.startupProject.Slider;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.configuration.WebSiteConfigurationProperties;
import com.ws.startupProject.file.FileService;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionProductToImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderService {
    @Autowired
    SliderRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;


    // Slider ait resim bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public Slider getSlider(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionProductToImage(id));
    }

    // Slider fotoğraflarının kaydedilmesi
    public void save(Slider slider, CurrentUser currentUser) {
        if (currentUser != null) {
            if (slider.getImage() != null) {
                String filename = fileService.saveBase64StringAsFile(slider.getImage(), properties.getStorage().getSlider(), slider.getName());
                slider.setImage(filename);
            }
            repository.save(slider);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Slider fotoğraflarının listeleme alanı
    public List<Slider> getSliderList() {
        return repository.findAll();
    }

    // Slider fotoğraflarının silinmesi
    public void deleteSlider(String id) {
        Slider inDb = getSlider(id);
        if (inDb != null) {
            fileService.deleteImageFolder(properties.getStorage().getSlider(), inDb.getImage());
            repository.delete(inDb);
        }
    }

    // Slider fotoğraflarının güncellenmesi
    public Object updateSlider(Slider slider) {
        Slider inDb = getSlider(slider.id);
        if (inDb != null) {
            if (slider.getImage() != null) {
                fileService.deleteImageFolder(properties.getStorage().getSlider(), inDb.getImage());
                String filename = fileService.saveBase64StringAsFile(slider.getImage(), properties.getStorage().getSlider(), slider.getName());
                slider.setImage(filename);
            }
            inDb.setName(slider.name);
            inDb.setShortDetail(slider.shortDetail);
            inDb.setDetail(slider.detail);
            inDb.setLink(slider.link);
            inDb.setImage(slider.image);
        }
        return repository.save(inDb);
    }

}
