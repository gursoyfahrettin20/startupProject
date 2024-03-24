package com.ws.startupProject.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeValidator.class)
public @interface FileImageType {
    String message() default "Sadece {types} formatında yükleme yapınız.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] types();
}
