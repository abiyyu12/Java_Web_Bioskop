package com.abiyyu.projects.libary.constraint;

import com.abiyyu.projects.libary.dto.AdminDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, AdminDto> {

    private String messageTemplate;

    @Override
    public void initialize(CheckPassword constraintAnnotation) {
        messageTemplate = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(AdminDto adminDto, ConstraintValidatorContext constraintValidatorContext) {
        if(adminDto.getPassword() == null || adminDto.getRepeatPassword() == null){
            return true;//Skip Violations
        }
        boolean isValid = adminDto.getPassword().equals(adminDto.getRepeatPassword());

        if(!isValid){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate).addPropertyNode("password").addConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate).addPropertyNode("repeatPassword").addConstraintViolation();
        }

        return isValid;
    }
}
