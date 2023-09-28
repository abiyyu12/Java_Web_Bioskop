package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.service.CustomerService;
import com.abiyyu.projects.libary.service.FilmService;
import com.abiyyu.projects.libary.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = {"/index","/"},method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession httpSession, HttpServletRequest httpServletRequest){
        if(principal != null){
            httpSession.setAttribute("username",principal.getName());
        }
        String login = httpServletRequest.getParameter("login");
        if(login != null){
            model.addAttribute("isLogin",true);
        }
        List<ScheduleDto> scheduleForIndex = scheduleService.getScheduleForIndex();
        List<ScheduleDto> comingSoonForIndex = scheduleService.getComingSoonForIndex();
        model.addAttribute("name",httpSession.getAttribute("username"));
        model.addAttribute("title","Movies");
        model.addAttribute("filmWeeks",scheduleForIndex);
        model.addAttribute("filmComings",comingSoonForIndex);
        return "index";
    }

}
