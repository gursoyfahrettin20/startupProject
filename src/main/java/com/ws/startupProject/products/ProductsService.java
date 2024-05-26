package com.ws.startupProject.products;

import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.productToImages.ProductToImagesRepository;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.exception.NotFoundExceptionProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository repository;

    @Autowired
    ProductToImagesRepository productToImagesRepository;

    // Ürünlerin Bilgileri varmı diye kontrol eder, yoksa hata mesajı döner.
    public Products getProduct(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundExceptionProducts(id));
    }

    // Ürünlerin kaydedilmesi
    public void saveProduct(Products products, CurrentUser currentUser) {
        if (currentUser != null) {
            repository.save(products);
        } else {
            String message = Messages.getMessageForLocale("Kullanıcı Bulunamadı", LocaleContextHolder.getLocale());
            throw new ExceptionInInitializerError(message);
        }
    }

    // Ürünlerin listeleme alanı
    public Page<Products> getProducts(Pageable page, CurrentUser currentUser) {
        if (currentUser != null) {
            if (currentUser.getIsAdministrator()) {
                return repository.findAll(page);
            }
        }
        return null;
    }

    // Ürünlerin listeleme alanı
    public Page<Products> getWProducts(Pageable page) {
        return repository.findAll(page);
    }

    // Ürünlerin silinmesi
    public void deleteProducts(String id) {
        Products inDb = getProduct(id);
        if (inDb != null) {

            //  buraya ürün resimleri altında bir veya birden fazla ürün varsa silinmeyecek kuralı eklenmesi gerekiyor.

            repository.delete(inDb);
        }
    }

    // Ürünlerin güncellenmesi
    public Object updateProducts(Products products) {
        Products inDb = getProduct(products.id);
        if (inDb != null) {
            inDb.setName(products.name);
            inDb.setDetail(products.detail);
            inDb.setProductToImages(products.productToImages);
        }
        return repository.save(inDb);
    }

}
