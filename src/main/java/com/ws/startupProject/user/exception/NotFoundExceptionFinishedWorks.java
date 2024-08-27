package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundExceptionFinishedWorks extends RuntimeException {
    public NotFoundExceptionFinishedWorks(String id) {
        super(Messages.getMessageForLocale("Referans Bulunamadı", LocaleContextHolder.getLocale(), id));
    }
}
