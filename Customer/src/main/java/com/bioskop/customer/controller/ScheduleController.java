package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.dto.ChairDto;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.service.ChairService;
import com.abiyyu.projects.libary.service.FilmService;
import com.abiyyu.projects.libary.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private FilmService filmService;

    @GetMapping("/schedule")
    public String schedule(Model model){
        model.addAttribute("title","Movie | Schedule");
        model.addAttribute("films",scheduleService.getFilmBySchedule());
        model.addAttribute("days",filmService.getDateCountFilm());
        return "schedule";
    }
}
