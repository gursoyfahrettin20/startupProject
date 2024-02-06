package com.ws.startupProject.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.ws.startupProject.shared.Messages;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super(Messages.getMessageForLocale("website.messages.user.not.found", LocaleContextHolder.getLocale(), id));
    }
}
