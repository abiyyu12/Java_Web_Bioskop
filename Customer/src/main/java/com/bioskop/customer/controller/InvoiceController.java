package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.entity.Order;
import com.abiyyu.projects.libary.service.ChairService;
import com.abiyyu.projects.libary.service.CustomerService;
import com.abiyyu.projects.libary.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class InvoiceController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ChairService chairService;

    @GetMapping("/invoice/{id}")
    public String getInvoice(@PathVariable("id") Long orderId, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        try{
            Order order = orderService.findOrder(orderId);
            model.addAttribute("title","Invoice");
            model.addAttribute("order",order);
            model.addAttribute("chairs",chairService.findChairByOrder(order.getId()));
        }catch (NoSuchElementException exception){
            // DO Anything if error get ID
            return "redirect:/";
        }
        return "invoice";
    }
}
