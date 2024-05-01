package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundExceptionCategories extends RuntimeException {
    public NotFoundExceptionCategories(String id) {
        super(Messages.getMessageForLocale("Kategori Bulunamadı", LocaleContextHolder.getLocale(), id));
    }
}
