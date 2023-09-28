package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.dto.CustomerDto;
import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title","Movies | Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title","Movies |  Register");
        model.addAttribute("customerDto",new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                             BindingResult result,
                             Model model){
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            }
            if (customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
            } else {
                model.addAttribute("error", "Password And Repeat Password Cannot Same");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
        }
        return "register";
    }
}
