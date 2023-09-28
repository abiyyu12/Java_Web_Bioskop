package com.abiyyu.projects.libary.utils;

import com.abiyyu.projects.libary.dto.AdminDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyValidator {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    public static Map<String,String> validate(AdminDto adminDto){
        Map<String,String> map = new HashMap<>();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> validate = validator.validate(adminDto);
        for(ConstraintViolation<Object> violation : validate){
            map.put(violation.getPropertyPath().toString(),violation.getMessage());
        }
        validatorFactory.close();
        return map;
    }
}
