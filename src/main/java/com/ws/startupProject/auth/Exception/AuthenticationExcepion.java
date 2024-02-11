package com.ws.startupProject.auth.Exception;

import org.springframework.context.i18n.LocaleContextHolder;
import com.ws.startupProject.shared.*;;

public class AuthenticationExcepion extends RuntimeException {

    public AuthenticationExcepion() {
        super(Messages.getMessageForLocale("website.messages.authentication.credentials",
                LocaleContextHolder.getLocale()));
    }

}
