package com.ws.startupProject.user.validation;

import com.ws.startupProject.file.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

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
        return false;
    }
}
