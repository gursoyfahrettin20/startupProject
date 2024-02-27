package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthorizationException  extends RuntimeException{
    public AuthorizationException(){
        super(Messages.getMessageForLocale("website.messages.user.authorizedUser", LocaleContextHolder.getLocale()));
    }
}
