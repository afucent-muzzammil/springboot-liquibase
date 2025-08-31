package com.example.sample.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})     // apply on class level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Password and Confirm Password do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
