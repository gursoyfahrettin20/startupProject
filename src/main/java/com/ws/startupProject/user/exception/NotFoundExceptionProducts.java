package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundExceptionProducts extends RuntimeException {
    public NotFoundExceptionProducts(String id) {
        super(Messages.getMessageForLocale("Ürün Bulunamadı", LocaleContextHolder.getLocale(), id));
    }
}
