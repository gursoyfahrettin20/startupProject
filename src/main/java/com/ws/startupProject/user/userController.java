package com.ws.startupProject.user;

import org.springframework.web.bind.annotation.RestController;

import com.ws.startupProject.error.ApiError;
import com.ws.startupProject.shared.GenericMessage;
import com.ws.startupProject.user.dto.UserCreate;
import com.ws.startupProject.user.exception.ActivationNotificationException;
import com.ws.startupProject.user.exception.NotUniqueEmailException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@CrossOrigin
@RestController
public class userController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        String message = messageSource.getMessage("website.messages.newuser", null, LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = messageSource.getMessage("website.messages.validationerror", null, LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);

        Map<String, String> validationErrors = new HashMap<>();
        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleNotEmailUniqueexception(NotUniqueEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = messageSource.getMessage("startupProject.messages.validationerror", null, LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);

        Map<String, String> UniqueEmailValidator = new HashMap<>();
        apiError.setValidationErrors(UniqueEmailValidator);
        return apiError;
    }
    @ExceptionHandler(ActivationNotificationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    ApiError handleActivationNotificationException(ActivationNotificationException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = messageSource.getMessage("website.messages.create.email.failure", null, LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(502);
        return apiError;
    }
}
