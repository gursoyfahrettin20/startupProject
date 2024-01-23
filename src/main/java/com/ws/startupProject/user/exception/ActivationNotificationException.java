package com.ws.startupProject.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.ws.startupProject.shared.Messages;

public class ActivationNotificationException extends RuntimeException {

    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("startupProject.messages.create.email.failure", LocaleContextHolder.getLocale()));
    }

}
