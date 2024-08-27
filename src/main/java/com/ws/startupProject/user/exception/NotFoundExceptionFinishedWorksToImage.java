package com.ws.startupProject.user.exception;

import com.ws.startupProject.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundExceptionFinishedWorksToImage extends RuntimeException {
    public NotFoundExceptionFinishedWorksToImage(String id){
        super(Messages.getMessageForLocale("Referans Fotoğrafı Bulunamadı", LocaleContextHolder.getLocale(), id));
    }
}
