package com.ws.startupProject.news;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.news.dto.NewsCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NewsController {

    @Autowired
    NewsService service;


    // News resimlerinin kaydedilmesi
    @PostMapping("/newNews")
    GenericMessage createNews(@Valid @RequestBody NewsCreate newsCreate, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Siteye Giriş yapmadınız.");
        if (currentUser.getIsAdministrator()) {
            service.save(newsCreate.toNews(), currentUser);
            message = new GenericMessage("Image is created");
        }
        return message;
    }

    // News resimlerinin listelenmesi
    @GetMapping("/news")
    public List<News> getNews(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getIsAdministrator()) {
            return service.getNewsList();
        }
        return null;
    }

    // News resimlerinin silinmesi
    @DeleteMapping("/news/{id}")
    GenericMessage deleteNews(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.deleteNews(id);
            message = new GenericMessage("News image is Delete");
        }
        return message;
    }

    // News resimlerinin güncellenmesi
    @PutMapping("/news/{id}")
    GenericMessage updateNews(@Valid @PathVariable Long id,  @RequestBody News news, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator() && Objects.equals(id, currentUser.getId())) {
            service.updateNews(news);
            message = new GenericMessage("News image is Update");
        }
        return message;
    }
}
