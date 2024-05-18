package com.ws.startupProject.news;

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
public class NewsService {

    @Autowired
    NewsRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;

    // News ait resim bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public News getNews(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionProductToImage(id));
    }

    // News fotoğraflarının kaydedilmesi
    public void save(News news, CurrentUser currentUser) {
        if (currentUser != null) {
            if (news.getImage() != null && !news.getImage().equals("default")) {
                String filename = fileService.saveBase64StringAsFile(news.getImage(), properties.getStorage().getNews(), news.getName());
                news.setImage(filename);
            }
            repository.save(news);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // News fotoğraflarının listeleme alanı
    public List<News> getNewsList() {
        return repository.findAll();
    }

    // News fotoğraflarının silinmesi
    public void deleteNews(String id) {
        News inDb = getNews(id);
        if (inDb != null) {
            fileService.deleteImageFolder(properties.getStorage().getNews(), inDb.getImage());
            repository.delete(inDb);
        }
    }

    // News fotoğraflarının güncellenmesi
    public Object updateNews(News news) {
        News inDb = getNews(news.id);
        if (inDb != null) {
            if (news.getImage() != null && !news.getImage().equals("default")) {
                fileService.deleteImageFolder(properties.getStorage().getNews(), inDb.getImage());
                String filename = fileService.saveBase64StringAsFile(news.getImage(), properties.getStorage().getNews(), news.getName());
                news.setImage(filename);
            }
            inDb.setName(news.name);
            inDb.setShortDetail(news.shortDetail);
            inDb.setDetail(news.detail);
            inDb.setLink(news.link);
            inDb.setImage(news.image);
        }
        return repository.save(inDb);
    }

}
