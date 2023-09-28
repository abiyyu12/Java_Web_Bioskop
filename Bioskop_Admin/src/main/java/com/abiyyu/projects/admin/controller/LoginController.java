package com.abiyyu.projects.admin.controller;

import com.abiyyu.projects.libary.dto.AdminDto;
import com.abiyyu.projects.libary.dto.CountOrderStatus;
import com.abiyyu.projects.libary.entity.Admin;
import com.abiyyu.projects.libary.service.*;
import com.abiyyu.projects.libary.service.Impl.AdminServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StudioService studioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("title","Login Admin");
        return "login";
    }

    @GetMapping(value = {"/","/index"})
    public String home(Model model, Principal principal, HttpSession httpSession){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        try{
            Integer valueOf = Integer.valueOf(httpSession.getAttribute("loginIndexes").toString());
            model.addAttribute("isLogin",false);
        }catch (Exception e){
            httpSession.setAttribute("loginIndexes",1);
            model.addAttribute("isLogin",true);
        }
        httpSession.setAttribute("username",principal.getName());
        List<String> orderLabels = orderService.getLabelOrder();
        List<String> value = orderService.getValueOrder();
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Dashboard Admin");
        model.addAttribute("cFilm",filmService.countFilm());
        model.addAttribute("orderLabel",orderLabels);
        model.addAttribute("orderValue",value);
        model.addAttribute("cAdmin",adminService.countAdmin());
        model.addAttribute("cCustomer",customerService.countCustomer());
        model.addAttribute("cSchedule",scheduleService.countSchedule());
        model.addAttribute("cOrder",orderService.countOrder());
        model.addAttribute("cStudio",studioService.countStudio());
        return "index";
    }


    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("adminDto",new AdminDto());
        model.addAttribute("title","Register Admin");
        return "register";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult bindingResult, Model model)
    {
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                return"register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("emailError","Your Email has been Registered");
                return "register";
            }
            adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            adminService.save(adminDto);
            System.out.println("Success");
            model.addAttribute("success","Register Successfully");
            model.addAttribute("adminDto",adminDto);
        }catch (Exception e){
            model.addAttribute("errors","The Server has been Wrong");
            return "register";
        }
        return "login";
    }
}
