package com.abiyyu.projects.libary;

import com.abiyyu.projects.libary.dto.AdminDto;
import com.abiyyu.projects.libary.entity.Film;
import com.abiyyu.projects.libary.repositories.FilmRepository;
import com.abiyyu.projects.libary.service.Impl.FilmServiceImpl;
import com.abiyyu.projects.libary.utils.ImageUpload;
import com.abiyyu.projects.libary.utils.MyValidator;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class Test {
    @org.junit.jupiter.api.Test
    void test() {
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstName("Abiyyu");
        adminDto.setLastName("Zaky");
        adminDto.setPassword("AA");
        adminDto.setRepeatPassword("zz");
        adminDto.setUsername("");
        Map<String,String> map = MyValidator.validate(adminDto);
        String passwordValidation = map.get("password");
        boolean password = map.containsKey("password");
        System.out.println(passwordValidation);
        System.out.println(password);
        map.forEach((key, value) -> {
            System.out.println(key + " = " + value + " ");
        });
    }
}
