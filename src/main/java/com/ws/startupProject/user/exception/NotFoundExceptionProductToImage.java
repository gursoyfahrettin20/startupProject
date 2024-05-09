package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundExceptionProductToImage extends RuntimeException {
    public NotFoundExceptionProductToImage(String id) {
        super(Messages.getMessageForLocale("Ürün Fotoğrafı Bulunamadı", LocaleContextHolder.getLocale(), id));
    }
}
