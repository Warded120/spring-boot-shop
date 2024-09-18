package com.ivan.usercrud.validation.passwordMatch;

import com.ivan.usercrud.template.UserTemplate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchConstraintValidator implements ConstraintValidator<PasswordMatch, UserTemplate> {

    @Override
    public boolean isValid(UserTemplate userTemplate, ConstraintValidatorContext context) {
        if (userTemplate.getPassword() == null || userTemplate.getConfirmPassword() == null) {
            return false; // or false depending on how you want to handle null cases
        }

        boolean isValid = userTemplate.getPassword().equals(userTemplate.getConfirmPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
