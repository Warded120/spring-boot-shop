package com.ivan.usercrud.validation.imageUrl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageUrlConstraintValidator implements ConstraintValidator<ImageUrl, String> {

    private String regExp1 = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|jpeg|png|gif|bmp|webp|svg)$";
    private String regExp2 = "^data:image\\/(jpeg|png|gif|bmp|webp|svg);base64,[A-Za-z0-9+/]+={0,2}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        return s.matches(regExp1) || s.matches(regExp2);
    }
}
