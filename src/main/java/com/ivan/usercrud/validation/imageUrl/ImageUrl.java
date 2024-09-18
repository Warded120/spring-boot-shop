package com.ivan.usercrud.validation.imageUrl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ImageUrlConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageUrl {
    String message() default "Not a valid image URL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
