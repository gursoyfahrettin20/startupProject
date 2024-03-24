package com.ws.startupProject.user.validation;

import com.ws.startupProject.file.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileImageType, String> {

    @Autowired
    FileService fileService;

    String[] types;

    @Override
    public void initialize(FileImageType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        String type = fileService.detectType(value);

        for (String validType : types) {
            if (type.contains(validType)) {
                return true;
            }
        }
        // Burası userUpdate classında verilen FileImageType ın types alanına bakarak hangi formatta kabul edeceğinin validasyon çıktısını FileImageType classında bulunan message alanında {types}
        // bölgesine mesajı paslar.
        String ValidTypes = Arrays.stream(types).collect(Collectors.joining(", "));
        constraintValidatorContext.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
        hibernateConstraintValidatorContext.addMessageParameter("types", ValidTypes);
        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()).addConstraintViolation();
        return false;
    }
}
