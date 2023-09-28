package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.dto.CustomerDto;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.entity.Schedule;
import com.abiyyu.projects.libary.service.CustomerService;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/profile")
    public String getProfile(Model model,Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customer = customerService.getCustomer(username);
        model.addAttribute("title", "Movie | Profile");
        model.addAttribute("customerDto", customer);
        return "profile";

    }

    @PostMapping("/profile")
    public String saveProfile(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult bindingResult
            , RedirectAttributes redirectAttributes, Model model,HttpSession httpSession){
        try {
            if(bindingResult.hasErrors()){
                String username = httpSession.getAttribute("username").toString();
                CustomerDto customer = customerService.getCustomer(username);
                model.addAttribute("title","Movie | Profile");
                model.addAttribute("customerDto",customer);
                return "profile";
            }
            Customer update = customerService.update(customerDto);
            if(update!=null){
                redirectAttributes.addFlashAttribute("success","Your Data Has Been Updated");
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Server Errors");
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String saveNewPassword(Principal principal,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if(oldPassword == null || newPassword == null){
            redirectAttributes.addFlashAttribute("errors","Please Insert Field");
        }else if(customerService.changePass(customer,oldPassword,newPassword)){
            redirectAttributes.addFlashAttribute("success","Password Has Been Changed");
        }else{
            redirectAttributes.addFlashAttribute("errors","Wrong Old Password");
        }
        return "redirect:/profile";
    }
}
