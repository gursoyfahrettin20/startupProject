package com.ws.startupProject.user.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.ws.startupProject.user.User;
import com.ws.startupProject.user.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User inDb = userRepository.findByEmail(value);
        return inDb == null;

    }

}
