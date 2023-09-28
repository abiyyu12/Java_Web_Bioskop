package com.abiyyu.projects.admin.controller;
import com.abiyyu.projects.libary.dto.DateCountFilm;
import com.abiyyu.projects.libary.dto.DateCountFilmToDays;
import com.abiyyu.projects.libary.dto.FilmDto;
import com.abiyyu.projects.libary.dto.FilmSizeDto;
import com.abiyyu.projects.libary.entity.Film;
import com.abiyyu.projects.libary.service.Impl.FilmServiceImpl;
import com.abiyyu.projects.libary.service.Impl.StudioServiceImpl;
import com.abiyyu.projects.libary.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class FilmController {

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private StudioServiceImpl studioService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/film")
    public String getFilm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Admin | Film");
        model.addAttribute("films",filmService.getAllFilm());
        return "film";
    }

    @GetMapping("/film/new")
    public String addFilm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("filmDto",new FilmDto());
        model.addAttribute("title","Admin | New Film");
        model.addAttribute("posterErrors",false);
        model.addAttribute("heroErrors",false);
        model.addAttribute("studios",studioService.getStudio());
        return "addFilm";
    }

    @PostMapping("/film/add")
    public String saveFilm(@Valid @ModelAttribute("filmDto") FilmDto filmDto,BindingResult bindingResult,@RequestParam("postersImage") MultipartFile posters,
                           @RequestParam("heroImages") MultipartFile hero,RedirectAttributes redirectAttributes, Model model){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Add Film");
                model.addAttribute("filmDto",filmDto);
                if(posters.isEmpty()){
                    model.addAttribute("posterErrors",true);
                }
                else{
                    model.addAttribute("posterErrors",false);
                }
                if(hero.isEmpty()){
                    model.addAttribute("heroErrors",true);
                }else{
                    model.addAttribute("heroErrors",false);

                }
                System.out.println("Binding Result Errors");
                model.addAttribute("studios",studioService.getStudio());
                return "addFilm";
            }
            Film film = filmService.saveFilm(hero, posters, filmDto);
            if(film != null){
                redirectAttributes.addFlashAttribute("success","New Film Saved");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Server Errors");
            return "redirect:/film/new";
        }
        return "redirect:/film";
    }

    @RequestMapping(value = "/film/edit/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String findFilmById(@PathVariable("id") Long id,Model model,RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        try {
            FilmDto filmDto = filmService.getById(id);
            model.addAttribute("filmDto",filmDto);
            Long clearValue = orderService.statusClear();
            Long pendingValue = orderService.statusPending();
            Long cancelValue = orderService.statusCancel();
            model.addAttribute("clearValue",clearValue);
            model.addAttribute("pendingValue",pendingValue);
            model.addAttribute("cancelValue",cancelValue);
            model.addAttribute("title","Admin | Update Film");
            model.addAttribute("posterErrors",false);
            model.addAttribute("heroErrors",false);
            System.out.println("Film Name"+filmDto.getName());
            System.out.println("Film Studio"+filmDto.getStudio().getName());
            model.addAttribute("studios",studioService.getStudio());
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Find Genre By Id");
            return "redirect:/film";
        }
        return "updateFilm";
    }

    @PostMapping("/film/update")
    public String updateFilm(@Valid @ModelAttribute("filmDto") FilmDto filmDto,BindingResult bindingResult,@RequestParam("postersImage") MultipartFile posters,
                             @RequestParam("heroImages") MultipartFile hero,RedirectAttributes redirectAttributes, Model model){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Add Film");
                model.addAttribute("filmDto",filmDto);
                model.addAttribute("posterErrors",false);
                model.addAttribute("heroErrors",false);
                model.addAttribute("studios",studioService.getStudio());
                return "updateFilm";
            }
            Film film = filmService.updateFilm(hero, posters, filmDto);
            if(film != null){
                redirectAttributes.addFlashAttribute("success","Film Has Been Updated");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            model.addAttribute("filmDto",filmDto);
            model.addAttribute("title","Admin | Update Film");
            model.addAttribute("posterErrors",false);
            model.addAttribute("heroErrors",false);
            model.addAttribute("studios",studioService.getStudio());
            redirectAttributes.addFlashAttribute("errors", "Server Errors");
            return "redirect:/film/updateFilm";
        }
        return "redirect:/film";
    }

    @PostMapping("/film/delete")
    public String deleteFilm(@RequestParam("id") Long id,RedirectAttributes redirectAttributes){
        filmService.deleteById(id);
        redirectAttributes.addFlashAttribute("errors","Film has been deleted");
        return "redirect:/film";
    }
}
