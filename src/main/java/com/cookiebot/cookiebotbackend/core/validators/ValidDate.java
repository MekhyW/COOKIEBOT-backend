package com.cookiebot.cookiebotbackend.core.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validate that the annotated string is in YYYY/MM/DD Date format
 */

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {

  String message() default "invalid date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}