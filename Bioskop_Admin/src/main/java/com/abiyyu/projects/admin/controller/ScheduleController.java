package com.abiyyu.projects.admin.controller;

import com.abiyyu.projects.libary.dto.FilmDto;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Schedule;
import com.abiyyu.projects.libary.service.Impl.FilmServiceImpl;
import com.abiyyu.projects.libary.service.Impl.ScheduleServiceImpl;
import com.abiyyu.projects.libary.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/schedule")
    public String getSchedule(Model model){
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("schedules",scheduleService.getAllSchedule());
        model.addAttribute("title","Admin | Schedule");
        return "Schedule";
    }

    @GetMapping("/schedule/new")
    public String addSchedule(Model model){
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("scheduleDto",new ScheduleDto());
        model.addAttribute("films",filmService.getAllFilm());
        model.addAttribute("title","Add | Schedule");
        return "addSchedule";
    }

    @PostMapping("/schedule/add")
    public String newSchedule(@Valid @ModelAttribute("scheduleDto") ScheduleDto scheduleDto, BindingResult bindingResult
        , RedirectAttributes redirectAttributes,Model model){
        try {
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Add Schedule");
                model.addAttribute("scheduleDto",scheduleDto);
                model.addAttribute("films",filmService.getAllFilm());
                return "addSchedule";
            }
            System.out.println("Try To Save");
            Schedule schedule = scheduleService.saveSchedule(scheduleDto);
            if(schedule!=null){
                redirectAttributes.addFlashAttribute("success","New Schedule Saved");
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Server Errors");
            return "redirect:/schedule/new";
        }
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/schedule/edit/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String findById(@PathVariable("id") Long id, Model model,RedirectAttributes redirectAttributes){
        try{
            ScheduleDto scheduleServiceById = scheduleService.getById(id);
            Long clearValue = orderService.statusClear();
            Long pendingValue = orderService.statusPending();
            Long cancelValue = orderService.statusCancel();
            model.addAttribute("clearValue",clearValue);
            model.addAttribute("pendingValue",pendingValue);
            model.addAttribute("cancelValue",cancelValue);
            model.addAttribute("title","Admin | Edit Schedule");
            model.addAttribute("films",filmService.getAllFilm());
            model.addAttribute("scheduleDto",scheduleServiceById);
            System.out.println(scheduleServiceById.getRegularStock());
            System.out.println(scheduleServiceById.getPremiumStock());
//            LocalDate localDate = LocalDate.parse(scheduleServiceById.getDate());
//            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
//            System.out.println(dayOfWeek.toString());
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot Find Schedule By Id");
            return "redirect:/schedule";
        }
        return "editSchedule";
    }

    @PostMapping("/schedule/update")
    public String updateSchedule(
            @Valid @ModelAttribute("scheduleDto") ScheduleDto scheduleDto, BindingResult bindingResult
            , RedirectAttributes redirectAttributes,Model model
    ){
        try {
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Edit Schedule");
                model.addAttribute("scheduleDto",scheduleDto);
                model.addAttribute("films",filmService.getAllFilm());
                return "editSchedule";
            }
            Schedule schedule = scheduleService.updateSchedule(scheduleDto);
            if(schedule!=null){
                redirectAttributes.addFlashAttribute("success","Update Schedule Saved");
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Server Errors");
            return "redirect:/schedule/edit/"+scheduleDto.getId();
        }
        return "redirect:/schedule";
    }

    @PostMapping("/schedule/delete")
    public String deleteSchedule(@ModelAttribute ("id") Long id,RedirectAttributes redirectAttributes){
        try{
            scheduleService.deleteById(id);
            System.out.println(id);
            redirectAttributes.addFlashAttribute("success","Schedule Has Been Deleted");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Delete Schedule");
            return "redirect:/schedule";
        }
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/schedule/enable/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String enableSchedule(@ModelAttribute ("id") Long id,RedirectAttributes redirectAttributes){
        try{
            scheduleService.enableScheduleById(id);
            System.out.println(id);
            redirectAttributes.addFlashAttribute("success","Schedule Has Been Enabled");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Enable Schedule");
        }
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/schedule/disable/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String disableSchedule(@ModelAttribute ("id") Long id,RedirectAttributes redirectAttributes){
        try{
            scheduleService.disableScheduleById(id);
            redirectAttributes.addFlashAttribute("success","Schedule Has Been Disabled");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Disable Schedule");
        }
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/schedule/enableHero/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String enableScheduleHero(@ModelAttribute ("id") Long id,RedirectAttributes redirectAttributes){
        try{
            Schedule schedule = scheduleService.enableHero(id);
            redirectAttributes.addFlashAttribute("success","Schedule Hero Has Been Enabled");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Enable Schedule Hero");
        }
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/schedule/disableHero/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String disableScheduleHero(@ModelAttribute ("id") Long id,RedirectAttributes redirectAttributes){
        try{
            Schedule schedule = scheduleService.disableHero(id);
            redirectAttributes.addFlashAttribute("success","Schedule Hero Has Been Disabled");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Disable Schedule Hero");
        }
        return "redirect:/schedule";
    }
}