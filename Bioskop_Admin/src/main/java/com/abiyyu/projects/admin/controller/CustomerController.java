package com.abiyyu.projects.admin.controller;

import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.service.CustomerService;
import com.abiyyu.projects.libary.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/customers")
    public String getCustomer(Principal principal, Model model){
        if(principal == null){
            return "redirect:/login";
        }
        List<Customer> customers = customerService.getAllCustomer();
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Admin | Customers");
        model.addAttribute("customers",customers);
        return "customers";
    }
}