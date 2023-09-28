package com.abiyyu.projects.admin.controller;
import com.abiyyu.projects.libary.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String getOrder(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Admin | Orders");
        model.addAttribute("orders",orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/orders/success/{id}")
    public String successDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            orderService.successOrders(id);
            redirectAttributes.addFlashAttribute("success","Status Order Has Been Updated");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Success Orders");
            return "redirect:/orders";
        }
        return "redirect:/orders";
    }


    @PostMapping("/orders/delete")
    public String deleteStudio(@ModelAttribute("id") Long id, RedirectAttributes redirectAttributes){
        try{
            orderService.deleteOrders(id);
            redirectAttributes.addFlashAttribute("success","Order Has Been Deleted");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Delete Studio");
            return "redirect:/orders";
        }
        return "redirect:/orders";
    }
}
