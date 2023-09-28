package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class MovieController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/movies")
    public String movies(Model model){
        Random random = new Random();
        int rand = random.nextInt(4);
        if(rand <= 0){
            rand = 1;
        }
        model.addAttribute("title","Movie | Movies");;
        System.out.println(rand);
        model.addAttribute("rand",rand);
        model.addAttribute("heroes",filmService.getFilmByHeroes());
        model.addAttribute("rating",filmService.countRateFilms());
        model.addAttribute("films",filmService.getFilmEnable());
        return "movies";
    }
}
