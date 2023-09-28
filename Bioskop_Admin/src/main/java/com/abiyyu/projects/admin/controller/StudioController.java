package com.abiyyu.projects.admin.controller;
import com.abiyyu.projects.libary.dto.StudioDto;
import com.abiyyu.projects.libary.service.Impl.StudioServiceImpl;
import com.abiyyu.projects.libary.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudioController {
    @Autowired
    private StudioServiceImpl studioService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/studio")
    public String getAllStudio(Model model){
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Admin | Studio");
        model.addAttribute("studios",studioService.getStudio());
        return "studio";
    }
    @GetMapping("/studio/new")
    public String addStudio(Model model){
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Admin | Add Studio");
        model.addAttribute("studioDto",new StudioDto());
        return "addStudio";
    }

    @PostMapping("/studio/add")
    public String saveStudio(@Valid @ModelAttribute("studioDto") StudioDto studioDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Add Studio");
                model.addAttribute("adminDto",studioDto);
                return "addStudio";
            }
            boolean save = studioService.save(studioDto);
            if(save){
                redirectAttributes.addFlashAttribute("success","New Studio Saved");
            }
        }catch (DataIntegrityViolationException e1) {
            redirectAttributes.addFlashAttribute("errors", "Duplicate name of studio, please check again!");
            return "redirect:/studio/new";
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errors", "Server Errors");
            return "redirect:/studio/new";
        }
        return "redirect:/studio";
    }

    @GetMapping("/studio/edit/{id}")
    public String findStudio(@PathVariable("id") Long id,Model model,RedirectAttributes redirectAttributes){
        try {
            StudioDto studioDto = studioService.getStudioById(id);
            Long clearValue = orderService.statusClear();
            Long pendingValue = orderService.statusPending();
            Long cancelValue = orderService.statusCancel();
            model.addAttribute("clearValue",clearValue);
            model.addAttribute("pendingValue",pendingValue);
            model.addAttribute("cancelValue",cancelValue);
            model.addAttribute("title","Admin | Update Studio");
            model.addAttribute("studioDto",studioDto);
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Find Studio By Id");
            return "redirect:/studio";
        }
        return "editStudio";
    }
    @PostMapping("/studio/update")
    public String updateStudio(@Valid @ModelAttribute("studioDto") StudioDto studioDto,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("title","Admin | Update Studio");
                return "editGenre";
            }
            boolean save = studioService.editStudio(studioDto);
            if(save){
                redirectAttributes.addFlashAttribute("success","Studio Has Been Updated");
            }
        }catch (DataIntegrityViolationException e1) {
            redirectAttributes.addFlashAttribute("errors", "Duplicate name of Studio, please check again!");
            return "redirect:/studio/edit/"+studioDto.getId();
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errors", "Server Errors");
            return "redirect:/studio/edit/"+studioDto.getId();
        }
        return "redirect:/studio";
    }

    @PostMapping("/studio/delete")
    public String deleteStudio(@ModelAttribute ("id") Long id,RedirectAttributes redirectAttributes){
        try{
            studioService.deleteStudio(id);
            redirectAttributes.addFlashAttribute("success","Studio Has Been Deleted");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors", "Could Not Delete Studio");
            return "redirect:/studio";
        }
        return "redirect:/studio";
    }
}
