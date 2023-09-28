package com.abiyyu.projects.libary.service;

import com.abiyyu.projects.libary.dto.AdminDto;
import com.abiyyu.projects.libary.dto.AdminDtoProfile;
import com.abiyyu.projects.libary.entity.Admin;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService{
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);

    boolean updateAdmin(MultipartFile image, AdminDtoProfile adminDtoProfile);

    boolean changePassword(Admin admin,String oldPassword,String newPassword);

    Long countAdmin();

}
