package com.abiyyu.projects.libary.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CheckPasswordValidator.class})
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RUNTIME)
public @interface CheckPassword {
    String message() default "Password and Retype password must same";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
