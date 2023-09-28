package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.service.CustomerService;
import com.abiyyu.projects.libary.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class TransactionController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/transaction")
    public String getTransaction(Principal principal, HttpSession httpSession, Model model){
        if(principal == null){
            return "redirect:/login";
        }
        String username = httpSession.getAttribute("username").toString();
        Customer customer = customerService.findByUsername(username);
        model.addAttribute("title","Movie | Transaction");
        model.addAttribute("orders",orderService.getOrderByCustomer(customer.getId()));
        return "transaction";
    }
}
