package com.ws.startupProject.user;

import org.springframework.web.bind.annotation.RestController;

import com.ws.startupProject.error.ApiError;
import com.ws.startupProject.shared.GenericMessage;
import com.ws.startupProject.shared.Messages;
import com.ws.startupProject.user.dto.UserCreate;
import com.ws.startupProject.user.exception.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// @RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class userController {

    @Autowired
    UserService userService;

    // user creation area
    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("website.messages.newuser", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    //User activation area
    @PatchMapping("/api/v1/users/{token}/active") // PatchMapping user bilgilerinin tümünü güncellemek yerine bir kısmını güncellemek için kullanılır.
    GenericMessage activateUser(@PathVariable String token) {
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("website.messages.activate.user.success", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }
    
    //Specific custom validation's create
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("website.messages.validationerror", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);

        Map<String, String> validationErrors = new HashMap<>();
        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

    //Email specific creation validator
    @ExceptionHandler(NotUniqueEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleNotEmailUniqueexception(NotUniqueEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("website.messages.validationerror", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);

        Map<String, String> UniqueEmailValidator = new HashMap<>();
        apiError.setValidationErrors(UniqueEmailValidator);
        return apiError;
    }

    //Customization area for unknown errors
    @ExceptionHandler(ActivationNotificationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    ApiError handleActivationNotificationException(ActivationNotificationException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("website.messages.create.email.failure", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(502);
        return apiError;
    }

    //Customization area for unknown errors
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleInvalidTokenException(InvalidTokenException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("website.messages.activate.user.invalid", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);
        return apiError;
    }
}
