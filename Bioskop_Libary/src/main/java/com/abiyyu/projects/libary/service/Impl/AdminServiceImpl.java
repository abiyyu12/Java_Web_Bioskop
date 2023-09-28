package com.abiyyu.projects.libary.service.Impl;

import com.abiyyu.projects.libary.dto.AdminDto;
import com.abiyyu.projects.libary.dto.AdminDtoProfile;
import com.abiyyu.projects.libary.entity.Admin;
import com.abiyyu.projects.libary.entity.Film;
import com.abiyyu.projects.libary.repositories.AdminRepository;
import com.abiyyu.projects.libary.repositories.RoleRepository;
import com.abiyyu.projects.libary.service.AdminService;
import com.abiyyu.projects.libary.utils.ImageUpload;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final ImageUpload imageUpload;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public boolean updateAdmin(MultipartFile image, AdminDtoProfile adminDto) {
        try {
            Admin admin = adminRepository.findByUsername(adminDto.getUsername());
            if (image == null) {

            } else {
                if (imageUpload.checkExistFileAdminProfile(image)) {
                    admin.setImage(admin.getImage());
                } else {
                    imageUpload.uploadFileAdminProfile(image);
                    admin.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                }
            }
            admin.setFirstName(adminDto.getFirstName());
            admin.setLastName(adminDto.getLastName());
            admin.setPassword(adminDto.getPassword());
            admin.setUsername(adminDto.getUsername());
            Admin save = adminRepository.save(admin);
            if(save != null){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(Admin admin, String oldPassword, String newPassword) {
        boolean isSuccess;
        if(bCryptPasswordEncoder.matches(oldPassword,admin.getPassword()) == true){
            admin.setPassword(bCryptPasswordEncoder.encode(newPassword));
            adminRepository.save(admin);
            isSuccess = true;
        }else {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public Long countAdmin() {
        return adminRepository.count();
    }
}
