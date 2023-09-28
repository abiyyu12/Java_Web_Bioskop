package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Schedule;
import com.abiyyu.projects.libary.service.FilmService;
import com.abiyyu.projects.libary.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DescriptionController {
    @Autowired
    private FilmService filmService;

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/desc/{id}",method = {RequestMethod.GET,RequestMethod.PUT})
    public String getDescFilm(@PathVariable("id") Long id, Model model){
        ScheduleDto scheduleByFilmId = scheduleService.getById(id);
        model.addAttribute("title","Movie | Description");
        model.addAttribute("film",filmService.getById(scheduleByFilmId.getFilm().getId()));
        model.addAttribute("schedule",scheduleByFilmId);
        return "desc";
    }
}
