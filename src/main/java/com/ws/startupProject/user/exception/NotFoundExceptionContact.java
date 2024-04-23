package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundExceptionContact extends RuntimeException {
    public NotFoundExceptionContact(long id) {
        super(Messages.getMessageForLocale("website.contact.messages.notFoundContact", LocaleContextHolder.getLocale(), id));
    }
}
