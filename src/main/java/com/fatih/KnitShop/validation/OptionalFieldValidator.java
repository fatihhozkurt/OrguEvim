package com.fatih.KnitShop.validation;

import com.fatih.KnitShop.annotation.OptionalFieldValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

public class OptionalFieldValidator implements ConstraintValidator<OptionalFieldValidation, String> {

    private boolean notBlank;
    private int min;
    private int max;
    private String pattern;

    @Override
    public void initialize(OptionalFieldValidation constraintAnnotation) {

        this.notBlank = constraintAnnotation.notBlank();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if (notBlank && value.isBlank()) {
            return false;
        }

        if (value.length() < min || value.length() > max) {
            return false;
        }

        return value.matches(pattern);
    }
}
