package com.ws.startupProject.error;

import java.util.HashMap;
import java.util.Map;

import com.ws.startupProject.user.exception.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ws.startupProject.auth.Exception.AuthenticationExcepion;
import com.ws.startupProject.user.exception.AuthorizationException;
import com.ws.startupProject.shared.Messages;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            NotUniqueEmailException.class,
            ActivationNotificationException.class,
            InvalidTokenException.class,
            NotFoundException.class,
            EntityNotFoundException.class,
            AuthenticationExcepion.class,
            AuthorizationException.class
    })
    ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());

        // Customization NotValid exception, exception area for unknown errors
        if (exception instanceof MethodArgumentNotValidException) {
            String message = Messages.getMessageForLocale("website.messages.validationerror",
                    LocaleContextHolder.getLocale());
            apiError.setMessage(message);
            apiError.setStatus(400);
            Map<String, String> validationErrors = new HashMap<>();
            for (var fieldError : ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            apiError.setValidationErrors(validationErrors);
        } else if (exception instanceof NotUniqueEmailException) { // Email specific creation validator
            String message = Messages.getMessageForLocale("website.messages.validationerror",
                    LocaleContextHolder.getLocale());
            apiError.setMessage(message);
            apiError.setStatus(400);

            Map<String, String> UniqueEmailValidator = new HashMap<>();
            apiError.setValidationErrors(UniqueEmailValidator);
        } else if (exception instanceof ActivationNotificationException) { // Customization Notification Exception area for unknown errors
            String message = Messages.getMessageForLocale("website.messages.create.email.failure",
                    LocaleContextHolder.getLocale());
            apiError.setMessage(message);
            apiError.setStatus(502);
        } else if (exception instanceof InvalidTokenException) { // Customization Token Exception area for unknown errors
            String message = Messages.getMessageForLocale("website.messages.activate.user.invalid",
                    LocaleContextHolder.getLocale());
            apiError.setMessage(message);
            apiError.setStatus(400);
        } else if (exception instanceof EntityNotFoundException) { // Customization area for notFoundException errors (select id for user detail area)
            apiError.setStatus(404);
        } else if (exception instanceof NotFoundException) { // Customization area for Authentication errors (select id for user detail area)
            apiError.setStatus(404);
        }else if (exception instanceof AuthenticationExcepion) { // Customization area for Authentication errors (select id for user detail area)
            apiError.setStatus(401);
        }else if (exception instanceof AuthorizationException) { // Customization area for AuthorizationException errors (select id for user detail area)
            apiError.setStatus(403);
        }

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}
