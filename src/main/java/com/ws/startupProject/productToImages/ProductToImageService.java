package com.ws.startupProject.productToImages;

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
public class ProductToImageService {

    @Autowired
    ProductToImagesRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    WebSiteConfigurationProperties properties;


    // Ürüne ait resim bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public ProductToImages getProductToImage(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionProductToImage(id));
    }

    // Ürün fotoğraflarının kaydedilmesi
    public void saveProductToImage(ProductToImages productToImages, CurrentUser currentUser) {
        if (currentUser != null) {
            if (productToImages.getImage() != null) {
                String filename = fileService.saveBase64StringAsFileProductToImages(productToImages.getImage(), properties.getStorage().getProduct(),productToImages.products.getName());
                productToImages.setImage(filename);
            }
            repository.save(productToImages);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Ürün fotoğraflarının listeleme alanı
    public List<ProductToImages> getProducts() {
        return repository.findAll();
    }

    // Ürün fotoğraflarının silinmesi
    public void deleteProductToImage(String id) {
        ProductToImages inDb = getProductToImage(id);
        if (inDb != null) {
            repository.delete(inDb);
        }
    }

    // Ürün fotoğraflarının güncellenmesi
    public Object updateProductToImages(ProductToImages productToImages) {
        ProductToImages inDb = getProductToImage(productToImages.id);
        if (inDb != null) {
            inDb.setImage(productToImages.image);
        }
        return repository.save(inDb);
    }

}
