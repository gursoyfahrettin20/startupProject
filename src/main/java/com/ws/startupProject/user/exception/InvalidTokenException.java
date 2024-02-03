package com.ws.startupProject.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.ws.startupProject.shared.Messages;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super(Messages.getMessageForLocale("website.messages.activate.user.invalid", LocaleContextHolder.getLocale()));
    }
}
