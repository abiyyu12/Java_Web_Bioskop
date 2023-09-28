package com.abiyyu.projects.admin.controller;


import com.abiyyu.projects.libary.dto.AdminDto;
import com.abiyyu.projects.libary.dto.AdminDtoProfile;
import com.abiyyu.projects.libary.dto.FilmDto;
import com.abiyyu.projects.libary.entity.Admin;
import com.abiyyu.projects.libary.service.AdminService;
import com.abiyyu.projects.libary.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Admin admin = adminService.findByUsername(principal.getName());
        AdminDtoProfile adminDtoProfile = new AdminDtoProfile();
        adminDtoProfile.setId(admin.getId());
        adminDtoProfile.setFirstName(admin.getFirstName());
        adminDtoProfile.setLastName(admin.getLastName());
        adminDtoProfile.setUsername(admin.getUsername());
        adminDtoProfile.setImage(admin.getImage());
        adminDtoProfile.setPassword(admin.getPassword());
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Admin | Profile");
        model.addAttribute("profileAdmin",adminDtoProfile);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profileAdmin") AdminDtoProfile profileAdmin, BindingResult bindingResult,
                                @RequestParam("newImage") MultipartFile newImage, Model model, RedirectAttributes redirectAttributes){
        try {
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Profile");
                model.addAttribute("profileAdmin",profileAdmin);
                return "profile";
            }
            boolean updateAdmin = adminService.updateAdmin(newImage, profileAdmin);
            redirectAttributes.addFlashAttribute("success","New Profile Has Been Updated");
        }catch (Exception e){
            model.addAttribute("title","Admin | Profile");
            model.addAttribute("profileAdmin",profileAdmin);
            redirectAttributes.addFlashAttribute("errors","Servers Error");
        }
        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(Principal principal,@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,RedirectAttributes redirectAttributes){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Admin admin = adminService.findByUsername(username);
        if(oldPassword == null || newPassword == null){
            redirectAttributes.addFlashAttribute("errors","Please Insert Field");
        }else if(adminService.changePassword(admin,oldPassword,newPassword)){
            redirectAttributes.addFlashAttribute("success","Password Has Been Changed");
        }else {
            redirectAttributes.addFlashAttribute("errors","Cannot Change Password");
        }
        return "redirect:/profile";
    }
}
